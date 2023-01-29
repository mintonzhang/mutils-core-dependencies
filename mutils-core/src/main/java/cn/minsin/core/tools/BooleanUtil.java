package cn.minsin.core.tools;

import org.apache.commons.lang3.BooleanUtils;

/**
 * @author minton.zhang
 * @since 2021/3/30 10:07
 */
public class BooleanUtil extends BooleanUtils {

    public static String CHINESE_TURE = "是";
    public static String CHINESE_FALSE = "否";

    public static int parseIntValue(Boolean bool) {
        return parseIntValue(bool, 1, 0);
    }

    public static int parseIntValue(Boolean bool, int trueValue, int falseValue) {
        return Boolean.TRUE.equals(bool) ? trueValue : falseValue;
    }


    public static Integer parseIntegerValue(Boolean bool, int trueValue, int falseValue) {
        return parseIntValue(bool, trueValue, falseValue);
    }

    /**
     * 判断bool值
     *
     * @param bool       被判断的值
     * @param trueValue  ture表示的值
     * @param falseValue false表示的值
     * @param nullValue  bool值为空时的值
     * @return 值
     */
    public static <T> T parseValue(Boolean bool, T trueValue, T falseValue, T nullValue) {
        return bool == null ? nullValue
                : Boolean.TRUE.equals(bool) ? trueValue : falseValue;
    }

    public static String parseValueToChinese(Boolean bool) {
        return parseValue(bool, CHINESE_TURE, CHINESE_FALSE);
    }

    /**
     * 判断bool值
     *
     * @param bool       被判断的值
     * @param trueValue  ture表示的值
     * @param falseValue false表示的值 bool为null时也是false值
     * @return 值
     */
    public static <T> T parseValue(Boolean bool, T trueValue, T falseValue) {
        return parseValue(bool, trueValue, falseValue, falseValue);
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
