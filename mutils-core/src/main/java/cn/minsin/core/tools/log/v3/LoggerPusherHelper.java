package cn.minsin.core.tools.log.v3;

import cn.minsin.core.tools.FormatStringUtil;
import cn.minsin.core.tools.StringUtil;
import cn.minsin.core.tools.log.common.LoggerConstant;
import cn.minsin.core.tools.log.common.LoggerHelperConfig;
import cn.minsin.core.tools.log.common.reporeies.ErrorReporter;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @author minton.zhang
 * @since 2021/11/11 15:48
 */
public class LoggerPusherHelper {

    private final static ConcurrentHashMap<String, Logger> LOGGER_MAP = new ConcurrentHashMap<>();
    public static LoggerHelperConfig DEFAULT_LOGGER_CONFIG;
    public final Logger logger;
    public final LoggerHelperConfig loggerHelperConfig;

    public LoggerPusherHelper(String loggerName, LoggerHelperConfig loggerHelperConfig) {
        this.logger = this.getLogger(loggerName);
        this.loggerHelperConfig = loggerHelperConfig;
    }

    public static LoggerPusherHelper of(String loggerName) {
        return new LoggerPusherHelper(loggerName, DEFAULT_LOGGER_CONFIG);
    }

    public static LoggerPusherHelper of(Enum<?> loggerEnum) {
        return new LoggerPusherHelper(loggerEnum.name(), DEFAULT_LOGGER_CONFIG);
    }

    //********************************分割线****************************************//

    public static LoggerPusherHelper of(String loggerName, LoggerHelperConfig loggerHelperConfig) {
        return new LoggerPusherHelper(loggerName, loggerHelperConfig);
    }

    public static LoggerPusherHelper of(Enum<?> loggerEnum, LoggerHelperConfig loggerHelperConfig) {
        return new LoggerPusherHelper(loggerEnum.name(), loggerHelperConfig);
    }

    protected Logger createLogger(String loggerName) {
        synchronized (LOGGER_MAP) {
            return LoggerFactory.getLogger(loggerName);
        }
    }

    public Logger getLogger(String loggerName) {
        return LOGGER_MAP.computeIfAbsent(loggerName, k -> this.createLogger(loggerName));
    }


    //********************************分割线****************************************//

    public void error(Throwable throwable, String errorStack, String errorMessage) {
        logger.error(errorMessage, throwable);
        List<ErrorReporter> reporter = loggerHelperConfig.getErrorReporters();
        ExecutorService executorService = loggerHelperConfig.getExecutorService();

        if (CollectionUtils.isNotEmpty(reporter)) {
            for (ErrorReporter errorReporter : reporter) {
                if (executorService != null) {
                    executorService.execute(errorReporter.getRunnable(throwable, errorMessage, errorStack));
                } else {
                    errorReporter.getRunnable(throwable, errorMessage, errorStack).run();

                }
            }
        }
    }


    public void error(Throwable error) {
        String stackMessage = loggerHelperConfig.getLoggerBodyFormatter().getErrorMessage(error, loggerHelperConfig);
        error(error, stackMessage, error.getMessage());
    }

    public void error(Throwable error, @NonNull String message, Object... param) {
        String msg = FormatStringUtil.format(message, param);
        String stackMessage = loggerHelperConfig.getLoggerBodyFormatter().getErrorMessage(error, loggerHelperConfig);
        error(error, stackMessage, msg + "\n" + error.getMessage());
    }

    public void error(@NonNull String message, Throwable error) {
        String errorStack = loggerHelperConfig.getLoggerBodyFormatter().getErrorMessage(error, loggerHelperConfig);
        error(error, errorStack, message);
    }

    public void error(@NonNull String message, Object... param) {
        String msg = FormatStringUtil.format(message, param);
        error(null, StringUtil.EMPTY, msg);
    }


    public void warn(@NonNull String message, Object... param) {
        logger.warn(message, param);
    }

    public void warn(@NonNull String message, Throwable param) {
        logger.warn(message, param);
    }

    /**
     * 记录轨迹日志 自定义方法 实际是debug
     */
    public void trace(@NonNull String message, Object... param) {
        logger.info(message, param);
    }


    /**
     * 记录轨迹日志 自定义方法 实际是info
     */
    public void beginTrace(@NonNull String message, Object... param) {
        logger.info("开始轨迹追踪,当前时间:".concat(formatDate()).concat(LoggerConstant.LOG_SPLIT).concat(message), param);
    }

    /**
     * 记录轨迹日志 自定义方法 实际是info
     */
    public void errorTrace(Throwable throwable, @NonNull String message, Object... param) {
        logger.error("轨迹追踪中出现异常(可在error或track中查看详情),当前时间:"
                .concat(formatDate())
                .concat(LoggerConstant.LOG_SPLIT)
                .concat("异常消息:" + throwable.getMessage())
                .concat(LoggerConstant.LOG_SPLIT)
                .concat(message), param);
    }

    /**
     * 记录轨迹日志 自定义方法 实际是info
     */
    public void endTrace(@NonNull String message, Object... param) {
        logger.info("结束轨迹追踪,当前时间:".concat(formatDate()).concat(LoggerConstant.LOG_SPLIT).concat(message), param);
    }

    public void info(@NonNull String message, Object... param) {
        logger.info(message, param);
    }


    public String formatDate() {
        return LocalDateTime.now().format(LoggerConstant.DATE_TIME_FORMATTER);
    }
}
