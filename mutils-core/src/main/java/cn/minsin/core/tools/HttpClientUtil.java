package cn.minsin.core.tools;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * httpClient帮助类
 * 可以参考 {@link HttpClients} 这个帮助类
 *
 * @author minsin
 */
public class HttpClientUtil {
    private static final int DEFAULT_TIMEOUT = 10000;

    /**
     * 获取SSL验证的HttpClient
     *
     * @param password          密码
     * @param certificatePath   证书地址
     * @param certificateFormat 证书格式
     */
    public static CloseableHttpClient getSSLInstance(String password, String certificatePath,
                                                     String certificateFormat) {
        try {
            BasicHttpClientConnectionManager connManager;
            // 证书
            char[] passwordChar = password.toCharArray();
            InputStream instream = new BufferedInputStream(new FileInputStream(certificatePath));
            KeyStore ks = KeyStore.getInstance(certificateFormat);
            ks.load(instream, passwordChar);

            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, passwordChar);

            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    new String[]{"TLSv1"}, null, new DefaultHostnameVerifier());

            connManager = new BasicHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocketFactory).build(), null, null, null);
            return HttpClientBuilder.create().setConnectionManager(connManager).build();
        } catch (Exception e) {
            e.printStackTrace();
            return HttpClientBuilder.create().build();
        }
    }

    /**
     * 创建一个普通实例
     */
    public static CloseableHttpClient getInstance() {
        BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(RegistryBuilder
                .<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory()).build(), null, null, null);
        return HttpClientBuilder.create().setConnectionManager(connManager).build();
    }

    /**
     * 模拟浏览器post提交
     *
     * @param url
     */
    public static HttpPost getPostMethod(String url) {
        HttpPost pmethod = new HttpPost(url);
        pmethod.addHeader("Connection", "keep-alive");
        pmethod.addHeader("Accept", "*/*");
        pmethod.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        pmethod.addHeader("Host", "api.mch.weixin.qq.com");
        pmethod.addHeader("X-Requested-With", "XMLHttpRequest");
        pmethod.addHeader("Cache-Control", "max-age=0");
        pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        return pmethod;
    }

    /**
     * 模拟浏览器GET提交
     *
     * @param url
     */
    public static HttpGet getGetMethod(String url) {
        HttpGet pmethod = new HttpGet(url);
        pmethod.addHeader("Connection", "keep-alive");
        pmethod.addHeader("Cache-Control", "max-age=0");
        pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        pmethod.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/;q=0.8");
        return pmethod;
    }

    /**
     * post 方法
     *
     * @param url
     * @param params
     * @throws IOException
     */
    public static String post(String url, Map<String, String> params) throws IOException {
        if (params == null || params.isEmpty()) {
            return "";
        }

        CloseableHttpClient httpClient = getInstance();
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig
                    .custom()
                    .setSocketTimeout(DEFAULT_TIMEOUT)
                    .setConnectTimeout(DEFAULT_TIMEOUT)
                    .build();//设置请求和传输超时时间

            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            List<BasicNameValuePair> basicNameValuePairs = new ArrayList<>();
            for (Map.Entry<String, String> entity : params.entrySet()) {
                basicNameValuePairs.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
            }

            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(basicNameValuePairs, StandardCharsets.UTF_8);
            httpPost.setEntity(urlEncodedFormEntity);

            response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            return result == null ? "" : result.trim();

        } finally {
            IOUtil.close(response, httpClient);
        }

    }
}
