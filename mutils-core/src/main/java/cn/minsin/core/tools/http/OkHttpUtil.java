package cn.minsin.core.tools.http;

import cn.minsin.core.tools.IOUtil;
import com.alibaba.fastjson2.JSON;
import lombok.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author minton.zhang
 * @since 2020/7/15 15:24
 */
public class OkHttpUtil {
	private final OkHttpClient client;

	private OkHttpUtil(OkHttpClient client) {
		this.client = client;
	}

	public static OkHttpUtil init(@NonNull OkHttpClient client) {
		return new OkHttpUtil(client);
	}

	/**
	 * 发送同步请求
	 *
	 * @param request  request对象
	 * @param function 转换函数
	 * @see OkHttpRequestBodyUtil 便捷构造请求体
	 * @see OkHttpRequestUtil 便捷构造请求
	 */
	public <T> T execute(Request request, Function<Response, T> function) throws IOException {
		Call call = client.newCall(request);

		try (Response execute = call.execute()) {
			return function.apply(execute);
		}
	}

	/**
	 * 发送异步请求
	 *
	 * @param request  请求对象
	 * @param callback 回调函数
	 * @see OkHttpRequestBodyUtil 便捷构造请求体
	 * @see OkHttpRequestUtil 便捷构造请求
	 */
	public void enqueue(Request request, Callback callback) {
		Call call = client.newCall(request);
		call.enqueue(callback);
	}


	/**
	 * 构造Request对象
	 */
	public interface OkHttpRequestUtil {

		static Request create(@NonNull String url, @NonNull RequestMethod requestMethod, RequestBody requestBody, Consumer<Request.Builder> func) {
			//	GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE
			requestBody = Optional.ofNullable(requestBody).orElse(OkHttpRequestBodyUtil.empty());
			okhttp3.Request.Builder builder = new okhttp3
					.Request.Builder()
					.url(url);
			switch (requestMethod) {
				case GET:
					builder.get();
					break;
				case HEAD:
					builder.head();
					break;
				case POST:
					builder.post(requestBody);
					break;
				case PUT:
					builder.put(requestBody);
					break;
				case PATCH:
					builder.patch(requestBody);
					break;
				case DELETE:
					builder.delete(requestBody);
					break;
			}
			if (func != null) {
				func.accept(builder);
			}
			return builder.build();
		}
	}

	/**
	 * 构造requestBody
	 */
	public interface OkHttpRequestBodyUtil {

		/**
		 * 文件上传
		 *
		 * @param file    需要上传的文件
		 * @param fileKey formdata 中文件的key
		 */
		static RequestBody fileUpload(File file, String fileKey) {
			return new MultipartBody.Builder()
					.setType(MultipartBody.FORM)
					.addFormDataPart(fileKey, file.getName(), RequestBody.create(file, MediaType.parse(MediaTypeConstant.MULTIPART_FORM_DATA)))
					.build();
		}

		/**
		 * 文件上传
		 *
		 * @param file     需要上传的文件
		 * @param fileKey  formdata 中文件的key
		 * @param fileName 上传到服务端的名称
		 */
		static RequestBody fileUpload(File file, String fileName, String fileKey) {
			return new MultipartBody.Builder()
					.setType(MultipartBody.FORM)
					.addFormDataPart(fileKey, fileName, RequestBody.create(file, MediaType.parse(MediaTypeConstant.MULTIPART_FORM_DATA)))
					.build();
		}

		/**
		 * 文件上传
		 *
		 * @param file     需要上传的文件流
		 * @param fileKey  formdata 中文件的key
		 * @param fileName 上传到服务端的名称
		 */
		static RequestBody fileUpload(InputStream file, String fileName, String fileKey) throws IOException {
			return new MultipartBody.Builder()
					.setType(MultipartBody.FORM)
					.addFormDataPart(fileKey, fileName, RequestBody.create(IOUtil.readAllBytes(file), MediaType.parse(MediaTypeConstant.MULTIPART_FORM_DATA)))
					.build();
		}

		/**
		 * 将Map转换成json 并封装成requestBody
		 */
		static RequestBody json(@NonNull Map<String, Object> param) {
			return RequestBody.create(JSON.toJSONString(param), MediaType.parse(MediaTypeConstant.APPLICATION_JSON_UTF8));
		}

		/**
		 * 将Object转换成json 并封装成requestBody
		 */
		static RequestBody json(@NonNull Object param) {
			return RequestBody.create(JSON.toJSONString(param), MediaType.parse(MediaTypeConstant.APPLICATION_JSON_UTF8));
		}

		/**
		 * 将Map转换成json 并封装成requestBody
		 */
		static RequestBody json(@NonNull String jsonString) {
			return RequestBody.create(jsonString, MediaType.parse(MediaTypeConstant.APPLICATION_JSON_UTF8));
		}

		static RequestBody empty() {
			return okhttp3.internal.Util.EMPTY_REQUEST;
		}

		/**
		 * 请求参数转换为url路径
		 *
		 * @param url 请求路径
		 * @param map 参数
		 */
		static String paramToUrl(String url, @NonNull Map<String, Serializable> map) {
			if (map.isEmpty()) {
				return url;
			}
			StringBuilder sb = new StringBuilder(url).append("?");
			for (Map.Entry<String, Serializable> entry : map.entrySet()) {
				sb.append(entry.getKey()).append("=").append(entry.getValue());
				sb.append("&");
			}
			String s = sb.toString();
			return url.concat(s.endsWith("&") ? StringUtils.substringBeforeLast(s, "&") : s);
		}
	}
}
