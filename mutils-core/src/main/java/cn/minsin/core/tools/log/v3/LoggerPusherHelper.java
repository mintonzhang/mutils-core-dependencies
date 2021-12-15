package cn.minsin.core.tools.log.v3;

import cn.minsin.core.tools.FormatStringUtil;
import cn.minsin.core.tools.log.common.BaseJsonObjectReportRequest;
import cn.minsin.core.tools.log.common.LoggerConstant;
import cn.minsin.core.tools.log.common.LoggerHelperConfig;
import cn.minsin.core.tools.log.common.reporeies.BaseErrorReporter;
import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;

/**
 * @author minton.zhang
 * @since 2021/11/11 15:48
 */
public class LoggerPusherHelper {

    private static LoggerHelperConfig DEFAULT_LOGGER_CONFIG;
    private static boolean isSetDefaultConfig = false;
    @Getter
    private final Logger logger;
    @Getter
    private final LoggerHelperConfig loggerHelperConfig;

    /**
     * 设置默认的config 只允许设置一次
     */
    public static void setDefaultLoggerConfig(LoggerHelperConfig defaultLoggerConfig) {


        if (!isSetDefaultConfig) {
            DEFAULT_LOGGER_CONFIG = defaultLoggerConfig;
            isSetDefaultConfig = true;
        }
    }


    public LoggerPusherHelper(Logger logger, @NonNull LoggerHelperConfig loggerHelperConfig) {
        this.logger = logger;
        this.loggerHelperConfig = loggerHelperConfig;
    }
    //********************************分割线****************************************//

    public static LoggerPusherHelper of(Logger logger) {

        return new LoggerPusherHelper(logger, DEFAULT_LOGGER_CONFIG);
    }


    public static LoggerPusherHelper of(Logger logger, LoggerHelperConfig loggerHelperConfig) {
        return new LoggerPusherHelper(logger, loggerHelperConfig);
    }
    //********************************分割线****************************************//


    public void error(Throwable throwable, String errorMessage) {
        logger.error(errorMessage, throwable);
        ExecutorService executorService = loggerHelperConfig.getExecutorService();

        for (BaseErrorReporter baseErrorReporter : loggerHelperConfig.getErrorReporters()) {
            if (baseErrorReporter.isDisable()) {
                continue;
            }
            if (executorService != null) {
                executorService.execute(baseErrorReporter.getRunnable(throwable, errorMessage));
            } else {
                baseErrorReporter.getRunnable(throwable, errorMessage).run();
            }
        }
    }


    public void error(Throwable error) {
        this.error(error, error.getMessage());
    }

    public void error(Throwable error, @NonNull String message, Object... param) {
        String msg = FormatStringUtil.format(message, param);
        this.error(error, msg);
    }

    public void error(@NonNull String message, Throwable error) {
        this.error(error, message);
    }

    public void error(@NonNull String message, Object... param) {
        String msg = FormatStringUtil.format(message, param);
        this.error(null, msg);
    }


    public void warn(@NonNull String message, Object... param) {
        logger.warn(message, param);
    }

    public void warn(@NonNull String message, Throwable param) {
        logger.warn(message, param);
    }

    public void warn(Throwable param) {
        logger.warn("warning", param);
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


    public void report(Throwable throwable, String errorMessage) {
        ExecutorService executorService = loggerHelperConfig.getExecutorService();
        for (BaseErrorReporter errorReporter : loggerHelperConfig.getErrorReporters()) {
            if (errorReporter.isDisable()) {
                continue;
            }
            if (executorService != null) {
                executorService.execute(errorReporter.getRunnable(throwable, errorMessage));
            } else {
                errorReporter.getRunnable(throwable, errorMessage).run();

            }
        }
    }

    public void reportJson(BaseJsonObjectReportRequest jsonObject) {
        ExecutorService executorService = loggerHelperConfig.getExecutorService();
        for (BaseErrorReporter errorReporter : loggerHelperConfig.getErrorReporters()) {
            if (errorReporter.isDisable()) {
                continue;
            }
            if (executorService != null) {
                executorService.execute(errorReporter.getRunnable(jsonObject));
            } else {
                errorReporter.getRunnable(jsonObject).run();

            }
        }
    }
}
