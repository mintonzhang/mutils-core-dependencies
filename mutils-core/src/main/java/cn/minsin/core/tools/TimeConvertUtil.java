package cn.minsin.core.tools;

import java.util.concurrent.TimeUnit;

/**
 * @author minton.zhang
 * @since 2021/5/18 13:18
 */
public interface TimeConvertUtil {

    /**
     * 转换成毫秒
     */
    static long convertMs(long timeout, TimeUnit timeUnit) {
        switch (timeUnit) {
            case MILLISECONDS:
                return timeout;
            case SECONDS:
                return timeout * 1000;
            case MINUTES:
                return timeout * 1000 * 60;
            case HOURS:
                return timeout * 1000 * 60 * 60;
            case DAYS:
                return timeout * 1000 * 60 * 60 * 24;
            default:
                throw new UnsupportedOperationException("不支持的timeUnit类型");
        }
    }

    static long convertMsWithCurrentMs(long timeout, TimeUnit timeUnit) {
        return System.currentTimeMillis() + convertMs(timeout, timeUnit);

    }
}
