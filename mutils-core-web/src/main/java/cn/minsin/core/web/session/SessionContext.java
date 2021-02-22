package cn.minsin.core.web.session;

import com.alibaba.fastjson.JSON;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Optional;

/**
 * 本地线程context
 *
 * @author minton.zhang
 * @since 2020/11/21 21:50
 */
@Slf4j
public class SessionContext {

	private static final ThreadLocal<Object> localMap = new ThreadLocal<>();

	public static <T extends Serializable> void set(@NonNull T serializable) {
		localMap.set(serializable);
	}

	public static <T> T get() {
		return (T) Optional.ofNullable(localMap.get()).orElse(null);
	}

	public static <T extends Serializable> void setJson(@NonNull T serializable) {
		localMap.set(JSON.toJSONString(serializable));
	}

	public static <T> T get(@NonNull Class<T> clazz) {
		return Optional.ofNullable(localMap.get()).map(e -> JSON.parseObject(e.toString(), clazz)).orElse(null);
	}


}
