package cn.minsin.core.tools;

import java.util.function.Function;

/**
 * @author minsin
 */
public interface Nvl {

    /**
     * if null
     */
    static <T> T ifNull(T checkData, T defaultData) {
        return checkData == null ? defaultData : checkData;
    }

    /**
     * if null
     */
    static <T, R> R ifNull(T checkData, R defaultValue, Function<T, R> defaultData) {
        return checkData == null ? defaultValue : defaultData.apply(checkData);
    }

    /**
     * if null
     */
    static int ifNull(Integer checkData, int defaultData) {
        return (checkData == null || checkData == 0) ? defaultData : checkData;
    }

    /**
     * if null
     */
    static long ifNull(Long checkData, long defaultData) {
        return (checkData == null || checkData == 0L) ? defaultData : checkData;
    }
}
