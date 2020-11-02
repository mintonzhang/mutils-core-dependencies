package cn.minsin.core.tools._assert;

import cn.minsin.core.tools.StringUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.function.Consumer;

/**
 * <pre>
 *     在{@linkplain CA#withException(String)}等默认抛出异常的方法中,
 *     当{@linkplain CA}的返回值为{@linkplain Boolean#TRUE} 时,则会抛出异常
 *     如果有特殊要求 可使用{@linkplain CA#with(Consumer)}进行手动处理
 * </pre>
 *
 * @author: minton.zhang
 * @since: 2020/5/26 17:22
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
	public static void withDefaultException(boolean condition, String msg) {
		withCustomException(condition, defaultException, msg);
	}

	/**
	 * 如果obejct是null,则抛出默认异常
	 *
	 * @param object
	 * @param msg
	 */
	public static void isNull(Object object, String msg) {
		withCustomException(StringUtil.isBlank(object), defaultException, msg);
	}

	/**
	 * 如果obejct是null,则抛出默认异常
	 *
	 * @param object
	 * @param msg
	 */
	public static void isNull(Object object, String msg, Class<? extends RuntimeException> clazz) {
		if (StringUtil.isBlank(object)) {
			createInstance(clazz, msg);

		}
	}


	/**
	 * 抛出自定义Exception
	 */
	public static void withCustomException(boolean condition, Class<? extends RuntimeException> clazz, String msg) {
		if (condition) {
			throw createInstance(clazz, msg);
		}
	}

	//********************************常用异常****************************************//

	/**
	 * 抛出运行时异常
	 */
	public static void withRuntimeException(boolean condition, String msg) {
		if (condition) {
			throw new RuntimeException(msg);
		}
	}

	/**
	 * 抛出空指针异常
	 */
	public static void withNullPointException(boolean condition, String msg) {
		if (condition) {
			throw new NullPointerException(msg);
		}
	}

	/**
	 * 抛出文件未找到异常
	 */
	public static void withFileNotFoundException(boolean condition, String msg) throws FileNotFoundException {
		if (condition) {
			throw new FileNotFoundException(msg);
		}
	}

	/**
	 * 抛出class未找到异常
	 */
	public static void withClassNotFoundException(boolean condition, String msg) throws ClassNotFoundException {
		if (condition) {
			throw new ClassNotFoundException(msg);
		}
	}

	/**
	 * 抛出下标越界异常
	 */
	public static void withIndexOutOfBoundsException(boolean condition, String msg) {
		if (condition) {
			throw new IndexOutOfBoundsException(msg);
		}
	}

	/**
	 * 抛出算数异常
	 */
	public static void withArithmeticException(boolean condition, String msg) {
		if (condition) {
			throw new ArithmeticException(msg);
		}
	}

	/**
	 * 抛出异常
	 */
	public static void withException(boolean condition, String msg) throws Exception {
		if (condition) {
			throw new Exception(msg);
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
	public static <T extends RuntimeException> T createInstance(Class<T> clazz, String message) {
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
