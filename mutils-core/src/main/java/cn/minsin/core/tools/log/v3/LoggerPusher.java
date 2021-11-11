package cn.minsin.core.tools.log.v3;

import cn.minsin.core.tools.FormatStringUtil;
import cn.minsin.core.tools.StringUtil;
import cn.minsin.core.tools.log.common.ErrorReporter;
import cn.minsin.core.tools.log.v2.LoggerHelperConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author minton.zhang
 * @since 2021/11/11 15:48
 */
@RequiredArgsConstructor
public class LoggerPusher {

    public final Logger logger;
    public final LoggerHelperConfig loggerHelperConfig;


    public void error(Throwable throwable, String errorStack, String errorMessage) {
        logger.error(errorMessage, throwable);
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
}
