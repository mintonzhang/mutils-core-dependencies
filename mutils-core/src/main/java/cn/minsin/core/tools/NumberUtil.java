package cn.minsin.core.tools;


import org.apache.commons.lang3.math.NumberUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;

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

    /**
     * 保留指定小数长度的数据
     */
    public static BigDecimal decimalsXLength(int length, BigDecimal old) {
        if (length < 0 || old == null) {
            return old;
        }
        return old.setScale(length, RoundingMode.HALF_UP);
    }

    /**
     * a+b and Keep 'decimal' decimals
     *
     * @param <T> must extend {@code Number}
     */
    public static <T extends Number> BigDecimal add(T a, T b, int decimal) {
        if (a != null && b != null) {
            return decimalsXLength(2, BigDecimal.valueOf(a.doubleValue()))
                    .add(BigDecimal.valueOf(b.doubleValue()))
                    .setScale(decimal,RoundingMode.DOWN);
        }
        return BigDecimal.valueOf(0);
    }

    /**
     * like a-b and Keep 'decimal' decimals
     *
     * @param <T> must extend {@code Number}
     */
    public static <T extends Number> BigDecimal subtract(T a, T b, int decimal) {
        if (a != null && b != null) {
            return decimalsXLength(2, BigDecimal.valueOf(a.doubleValue()))
                    .subtract(BigDecimal.valueOf(b.doubleValue()))
                    .setScale(decimal,RoundingMode.DOWN);
        }
        return BigDecimal.valueOf(0);
    }

    /**
     * a/b and Keep 'decimal' decimals
     *
     * @param <T> must extend {@code Number}
     */
    public static <T extends Number> BigDecimal divide(T a, T b, int decimal) {
        if (a != null && b != null) {
            return decimalsXLength(2, BigDecimal.valueOf(a.doubleValue()))
                    .divide(BigDecimal.valueOf(b.doubleValue()), decimal, RoundingMode.HALF_UP)
                    .setScale(decimal,RoundingMode.DOWN);
        }
        return BigDecimal.valueOf(0);
    }

    /**
     * a*b and Keep 'decimal' decimals
     *
     * @param <T> must extend {@code Number}
     */
    public static <T extends Number> BigDecimal multiply(T a, T b, int decimal) {
        if (a != null && b != null) {
            return decimalsXLength(2, BigDecimal.valueOf(a.doubleValue()))
                    .multiply(BigDecimal.valueOf(b.doubleValue()))
                    .setScale(decimal,RoundingMode.DOWN);
        }
        return BigDecimal.valueOf(0);
    }

    /**
     * a%b and Keep 'decimal' decimals
     *
     * @param <T> must extend {@code Number}
     */
    public static <T extends Number> BigDecimal remainder(T a, T b, int decimal) {
        if (a != null && b != null) {
            return decimalsXLength(2, BigDecimal.valueOf(a.doubleValue()))
                    .remainder(BigDecimal.valueOf(b.doubleValue()))
                    .setScale(decimal,RoundingMode.DOWN);
        }
        return BigDecimal.valueOf(0);
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
    public static <N> boolean in(N input, N... values) {
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
    public static <N> boolean notIn(N input, N... values) {
        return !in(input, values);
    }

    /**
     * 判断values等于input的值
     */
    public static <N> boolean eq(N input, N values) {
        if (input == null || values == null) {
            return false;
        }
        return input.equals(values);
    }

    /**
     * 判断values不等于input的值
     */
    public static <N> boolean ne(N input, N values) {
        return !eq(input, values);
    }
}
