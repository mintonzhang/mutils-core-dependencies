package cn.minsin.core.tools.log.common;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author minton.zhang
 * @since 2021/11/8 10:38
 */
@FunctionalInterface
public interface BaseLoggerBodyFormatter {

    /**
     * 解析异常详情
     */
    static String getStackTrace(Throwable exception) {
        return ExceptionUtils.getStackTrace(exception);
    }

    /**
     * 获取异常消息
     *
     * @param exception
     * @param config
     * @return
     */
    String getErrorMessage(Throwable exception, LoggerHelperConfig config);
}
