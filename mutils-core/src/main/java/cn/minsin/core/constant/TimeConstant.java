package cn.minsin.core.constant;

/**
 * @author: minton.zhang
 * @since: 2019/11/7 18:14
 */
public interface TimeConstant {

    /**
     * 用毫秒表示一分钟
     */
    long ONE_MINUTE_WITH_MILLISECOND = 60 * 1000;

    /**
     * 用毫秒表示10分钟
     */
    long TEN_MINUTES_WITH_MILLISECOND = ONE_MINUTE_WITH_MILLISECOND * 10;

    /**
     * 用秒表示一分钟
     */
    long ONE_MINUTE_WITH_SECOND = 60;

    /**
     * 用秒表示10分钟
     */
    long TEN_MINUTES_WITH_SECOND = ONE_MINUTE_WITH_SECOND * 10;


    long ONE_HOUR_WITH_MILLISECOND = TEN_MINUTES_WITH_MILLISECOND * 6;
}
