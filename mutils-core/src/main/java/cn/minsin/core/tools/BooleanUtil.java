package cn.minsin.core.tools;

import org.apache.commons.lang3.BooleanUtils;

/**
 * @author minton.zhang
 * @since 2021/3/30 10:07
 */
public class BooleanUtil extends BooleanUtils {

	public static int parseIntValue(Boolean bool) {
		return parseIntValue(bool, 1, 0);
	}

	public static int parseIntValue(Boolean bool, int trueValue, int falseValue) {
		return Boolean.TRUE.equals(bool) ? trueValue : falseValue;
	}


	public static Integer parseIntegerValue(Boolean bool, int trueValue, int falseValue) {
		return parseIntValue(bool, trueValue, falseValue);
	}

	public static Integer parseIntegerValue(Boolean bool) {
		return parseIntValue(bool);
	}

	/**
	 * 将number 转换为 boolean
	 * <pre>
	 *   BooleanUtil.parseBooleanValue(1D)  = true
	 *   BooleanUtil.parseBooleanValue(1.1D) =true
	 *   BooleanUtil.parseBooleanValue(1.9D) =true
	 *   BooleanUtil.parseBooleanValue(2.0D) =false
	 *   BooleanUtil.parseBooleanValue(1.9F) =true
	 *   BooleanUtil.parseBooleanValue(1F) =true
	 *   BooleanUtil.parseBooleanValue(2.0D) =false
	 *   BooleanUtil.parseBooleanValue(1L) =true
	 *   BooleanUtil.parseBooleanValue(1) =true
	 * </pre>
	 */
	public static boolean parseBooleanValue(Number number) {
		if (number == null) {
			return false;
		}
		return NumberUtil.INTEGER_ONE.equals(number.intValue());
	}

	public static boolean parseBooleanValueThenNegate(Number number) {
		return !parseBooleanValue(number);
	}

	public static int parseIntValueThenNegate(Boolean bool) {
		return parseIntValue(bool, 0, 1);
	}


	public static Integer parseIntegerValueThenNegate(Boolean bool) {
		return parseIntValueThenNegate(bool);
	}
}
