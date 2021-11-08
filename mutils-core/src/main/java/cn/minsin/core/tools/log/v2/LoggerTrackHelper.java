package cn.minsin.core.tools.log.v2;

import cn.minsin.core.tools.FormatStringUtil;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 1. 时间戳 timestamp
 * 2. 错误码(需要定义code码表) errorCode
 * 3. 错误消息 errorMsg
 * 4. 错误详细信息 errorDetail
 * 5. 系统接入方 sourceSystem
 * 6. ip地址 ip
 * 7. 机器号(设备号)  deviceCode
 * 8. 入参(json) inputParam
 * 9. 全局id globalId
 *
 * @author minton.zhang
 * @since 2020/12/16 上午11:28
 */
public class LoggerTrackHelper {

    static Logger logger;
    static LoggerHelperConfig loggerHelperConfig;


    public static void error(Throwable throwable, String errorStack, String errorMessage) {
        logger.error(errorMessage + "\n\n" + errorStack);
        List<ErrorReporter> reporter = loggerHelperConfig.getErrorReporters();
        ExecutorService executorService = loggerHelperConfig.getExecutorService();

        if (CollectionUtils.isNotEmpty(reporter)) {
            for (ErrorReporter errorReporter : reporter) {
                if (executorService != null) {
                    executorService.execute(() -> {
                        try {
                            errorReporter.report(throwable, errorMessage, errorStack);
                        } catch (Exception e) {
                            logger.error("错误报告上报失败 class{} ", errorReporter.getClass(), e);
                        }
                    });
                } else {
                    try {
                        errorReporter.report(throwable, errorMessage, errorStack);
                    } catch (Exception e) {
                        logger.error("错误报告上报失败 class{} ", errorReporter.getClass(), e);
                    }
                }
            }
        }
    }


    public static void error(Throwable error) {
        String stackMessage = loggerHelperConfig.getLoggerBodyFormatter().getErrorMessage(error, loggerHelperConfig);
        error(error, stackMessage, error.getMessage());
    }

    public static void error(Throwable error, @NonNull String message, Object... param) {
        String msg = FormatStringUtil.format(message, param);
        String stackMessage = loggerHelperConfig.getLoggerBodyFormatter().getErrorMessage(error, loggerHelperConfig);
        error(error, stackMessage, msg + "\n" + error.getMessage());
    }

    public static void error(@NonNull String message, Throwable error) {
        error(error, null, message);
    }

    public static void error(@NonNull String message, Object... param) {
        String msg = FormatStringUtil.format(message, param);
        error(null, null, msg);
    }

    public static void warn(@NonNull String message, Object... param) {
        logger.warn(message, param);
    }

    public static void warn(@NonNull String message, Throwable param) {
        logger.warn(message, param);
    }

    /**
     * 记录轨迹日志 自定义方法 实际是debug
     */
    public static void trace(@NonNull String message, Object... param) {
        logger.info(message, param);
    }


    /**
     * 记录轨迹日志 自定义方法 实际是info
     */
    public static void beginTrace(@NonNull String message, Object... param) {
        logger.info("开始轨迹追踪,当前时间:".concat(formatDate()).concat(LoggerConstant.LOG_SPLIT).concat(message), param);
    }

    /**
     * 记录轨迹日志 自定义方法 实际是info
     */
    public static void errorTrace(Throwable throwable, @NonNull String message, Object... param) {
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
    public static void endTrace(@NonNull String message, Object... param) {
        logger.info("结束轨迹追踪,当前时间:".concat(formatDate()).concat(LoggerConstant.LOG_SPLIT).concat(message), param);
    }

    public static void info(@NonNull String message, Object... param) {
        logger.info(message, param);
    }

    public static String parserException(Throwable e) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (PrintStream printStream = new PrintStream(byteArrayOutputStream)) {
            e.printStackTrace(printStream);
        } catch (Exception e1) {
            return e1.getMessage();
        }
        return byteArrayOutputStream.toString().replace("\r\n", LoggerConstant.LOG_SPLIT + "\r\n");
    }

    public static String formatDate() {
        return LocalDateTime.now().format(LoggerConstant.DATE_TIME_FORMATTER);
    }

}
