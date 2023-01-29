package cn.minsin.core.tools;


import cn.minsin.core.tools.math.CompareResult;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

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


    @Nullable
    public static <T extends Comparable<T>> CompareResult compareBy(T x, T y) {
        return compareValue(x, y, null, e -> e);
    }


    public static <T extends Comparable<T>> Optional<T> getMaxValue(T x, T y) {
        return compareValue(x, y, Optional.empty(), e -> CompareResult.GT.equals(e) ? Optional.of(x) : Optional.of(y));
    }

    public static <T extends Comparable<T>> Optional<T> getMinValue(T x, T y) {
        return compareValue(x, y, Optional.empty(), e -> CompareResult.LT.equals(e) ? Optional.of(x) : Optional.of(y));
    }

    public static <T extends Comparable<T>> T getMaxValueOrDefault(T x, T y, T defaultValue) {
        return compareValue(x, y, defaultValue, e -> CompareResult.GT.equals(e) ? x : y);
    }

    public static <T extends Comparable<T>> T getMinValueOrDefault(T x, T y, T defaultValue) {
        return compareValue(x, y, defaultValue, e -> CompareResult.LT.equals(e) ? x : y);
    }

    /**
     * 比较方法
     *
     * @param x        值1
     * @param y        值2
     * @param function 值处理器
     * @param <T>      T为可比较对象
     * @param <R>      R 未返回值对象
     * @return 返回结果
     */
    @NonNull
    public static <T extends Comparable<T>, R> R compareValue(T x, T y, R defaultValue, Function<CompareResult, R> function) {
        if (x == null || y == null) {
            return defaultValue;
        }

        CompareResult result;
        try {
            result = CompareResult.getResult(x.compareTo(y));
        } catch (Exception e) {
            return defaultValue;
        }
        return function.apply(result);
    }


}
