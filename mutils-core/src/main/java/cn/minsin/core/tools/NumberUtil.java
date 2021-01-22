package cn.minsin.core.tools;


import org.apache.commons.lang3.math.NumberUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * 数值工具类  对{@link NumberUtils} 进行拓展
 *
 * @author mintonzhang
 * @date 2019年2月14日
 * @since 0.1.0
 */
@SuppressWarnings("unchecked")
public class NumberUtil extends NumberUtils {

	protected NumberUtil() {
		// allow Subclass
	}

	/**
	 * 判断字符串是否是数字（无法具体判断是整型还是浮点型）
	 *
	 * @param str
	 */
	public static boolean isNumbers(String... str) {
		try {
			for (String string : str) {
				if (!NumberUtils.isCreatable(string)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是数字（指定类型）
	 *
	 * @param str
	 */
	public static <T extends Number> boolean isNumbers(Class<T> type, String... str) {
		try {
			Method method = type.getMethod("valueOf", String.class);
			for (String string : str) {
				method.invoke(type, string);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	//----------------------------------Split Line---------------------------------

	/**
	 * 数值校验 如果为true返回默认值
	 *
	 * @param input        需要验证的值
	 * @param compare      与验证值比较的值
	 * @param defaultValue 如果input为空或者 input与compare验证结果为true  则返回默认值
	 */
	public static <N> N validate(N input, N compare, N defaultValue) {

		return (input == null || compare == input) ? defaultValue : input;
	}

	/**
	 * 判断values是否包含input的值
	 */
	public static <N extends BigDecimal> boolean in(N input, N... values) {
		if (input == null || values == null) {
			return false;
		}
		for (N value : values) {
			if (input.equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断values是否包含input的值
	 */
	public static <N extends BigDecimal> boolean notIn(N input, N... values) {
		return !in(input, values);
	}

	/**
	 * 判断values等于input的值
	 */
	public static <N extends BigDecimal> boolean eq(N input, N values) {
		if (input == null || values == null) {
			return false;
		}
		return input.equals(values);
	}

	/**
	 * 判断values不等于input的值
	 */
	public static <N extends BigDecimal> boolean ne(N input, N values) {
		return !eq(input, values);
	}

	public static int parseInt(Number number) {
		return number == null ? 0 : number.intValue();
	}

	public static long parseLong(Number number) {
		return number == null ? 0 : number.longValue();
	}
}
