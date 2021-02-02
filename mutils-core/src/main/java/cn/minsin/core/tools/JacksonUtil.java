package cn.minsin.core.tools;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * @author: minton.zhang
 * @since: 2021/2/2 下午1:28
 */
@UtilityClass
@Slf4j
public class JacksonUtil {


	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		// 全部字段序列化
		//对象的所有字段全部列入
		objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		//设置默认转换timestamps形式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		//解决jdk8时间无法序列化
		objectMapper.registerModule(new JavaTimeModule());

		//忽略空Bean转json的错误
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		//忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public void setObjectMapper(@NonNull ObjectMapper objectMapper) {
		JacksonUtil.objectMapper = objectMapper;
	}

	public void setObjectMapperConfig(Consumer<ObjectMapper> consumer) {
		consumer.accept(JacksonUtil.objectMapper);
	}


	/**
	 * 将对象转换为json
	 */
	public <T> String serialize(T obj) {
		try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (Exception e) {
			log.error("jackson serialize failed.", e);
		}
		return null;
	}

	/**
	 * 字符串转对象
	 */
	public <T> T deserialize(String str, Class<T> clazz) {
		try {
			return objectMapper.readValue(str, clazz);
		} catch (Exception e) {
			log.error("jackson deserialize failed.", e);
		}
		return null;
	}

	/**
	 * 反序列化复杂对象
	 */
	public <T> T deserialize(String str, TypeReference<T> typeReference) {
		try {
			return objectMapper.readValue(str, typeReference);
		} catch (Exception e) {
			log.error("jackson deserialize failed.", e);
		}
		return null;

	}

	/**
	 * 字符串转对象
	 */
	public <T> T deserialize(byte[] str, Class<T> clazz) {
		try {
			return objectMapper.readValue(str, clazz);
		} catch (Exception e) {
			log.error("jackson deserialize failed.", e);
		}
		return null;
	}

	/**
	 * 反序列化复杂对象
	 */
	public <T> T deserialize(byte[] str, TypeReference<T> typeReference) {
		try {
			return objectMapper.readValue(str, typeReference);
		} catch (Exception e) {
			log.error("jackson deserialize failed.", e);
		}
		return null;

	}
}
