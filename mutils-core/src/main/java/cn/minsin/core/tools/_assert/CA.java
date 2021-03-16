package cn.minsin.core.tools._assert;

import cn.minsin.core.tools.NumberUtil;
import cn.minsin.core.tools.StringUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;

import static cn.minsin.core.tools.FormatStringUtil.format;

/**
 * <pre>
 *     当判断条件返回为true时抛出异常
 * </pre>
 *
 * @author minton.zhang
 * @since 2020/5/26 17:22
 */
@Slf4j
public abstract class CA {


	private static Class<? extends RuntimeException> defaultException = RuntimeException.class;

	/**
	 * 设置全局默认异常
	 * 必须实现此构造器{@linkplain RuntimeException#Throwable(String)}
	 */
	public static void setDefaultException(@NonNull Class<? extends RuntimeException> defaultException) {
		//检查构造器
		try {
			defaultException.getConstructor(String.class);
		} catch (Throwable e) {
			throw new RuntimeException("异常Class'" + defaultException + "'中必须要有RuntimeException#Throwable(String)构造器,如果是内部类需要声明为public static");
		}
		CA.defaultException = defaultException;
	}

	/**
	 * 使用默认的exception
	 *
	 * @param msg
	 */
	public static void withDefaultException(boolean condition, String msg, Object... param) {
		withCustomException(condition, defaultException, format(msg, param));
	}

	/**
	 * 如果condition是true,则抛出默认异常
	 *
	 * @param condition
	 * @param msg
	 */
	public static void isTrue(boolean condition, String msg, Object... param) {
		withCustomException(condition, defaultException, format(msg, param));
	}

	/**
	 * 如果condition是true,则抛出默认异常
	 */
	public static void isFalse(boolean condition, String msg, Object... param) {
		withCustomException(!condition, defaultException, format(msg, param));
	}

	/**
	 * 如果object是null,则抛出默认异常
	 *
	 * @param object
	 * @param msg
	 */
	public static void isNull(Object object, String msg, Object... param) {
		withCustomException(StringUtil.isBlank(object), defaultException, format(msg, param));
	}

	/**
	 * x>0
	 * 是否大于0
	 */
	public static void isGtZero(Number number, String msg, Object... param) {
		withCustomException(NumberUtil.parseInt(number) > 0, defaultException, format(msg, param));
	}

	/**
	 * x=0
	 * 是否等于0
	 */
	public static void isEqZero(Number number, String msg, Object... param) {
		withCustomException(NumberUtil.parseInt(number) == 0, defaultException, format(msg, param));
	}

	/**
	 * x<0
	 * 是否小于0
	 */
	public static void isLtZero(Number number, String msg, Object... param) {
		withCustomException(NumberUtil.parseInt(number) < 0, defaultException, format(msg, param));
	}


	/**
	 * 抛出自定义Exception
	 */
	protected static void withCustomException(boolean condition, Class<? extends RuntimeException> clazz, String msg, Object... param) {
		if (condition) {
			throw createInstance(clazz, format(msg, param));
		}
	}


	/**
	 * 通常来说不建议这样使用 因为不管是true还是false都会创建这个异常对象
	 */
	public static void withRuntimeException(boolean condition, RuntimeException exception) {
		if (condition) {
			throw exception;
		}
	}

	/**
	 * 通常来说不建议这样使用 因为不管是true还是false都会创建这个异常对象
	 */
	public static void withException(boolean condition, Exception exception) throws Exception {
		if (condition) {
			throw exception;
		}
	}


	/**
	 * 必须实现此构造器{@linkplain RuntimeException#Throwable(String)}
	 */
	protected static <T extends RuntimeException> T createInstance(Class<T> clazz, String message) {
		try {
			Constructor<? extends RuntimeException> constructor = clazz.getConstructor(String.class);
			throw constructor.newInstance(message);
		} catch (RuntimeException e) {
			throw e;
		} catch (Throwable e) {
			log.error("异常Class'{}'中必须要有RuntimeException#Throwable(String)构造器,如果是内部类需要声明为public static", clazz);
			throw new RuntimeException(message);
		}
	}


}
