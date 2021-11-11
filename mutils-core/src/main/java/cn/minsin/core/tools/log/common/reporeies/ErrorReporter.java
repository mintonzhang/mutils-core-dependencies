package cn.minsin.core.tools.log.common.reporeies;

import cn.minsin.core.tools.log.common.LoggerHelperConfig;
import org.slf4j.Logger;

/**
 * @author minton.zhang
 * @since 2021/11/8 10:28
 */
public abstract class ErrorReporter {
    protected final LoggerHelperConfig logHelperConfig;

    protected final Logger logger;

    public ErrorReporter(LoggerHelperConfig logHelperConfig) {
        this.logHelperConfig = logHelperConfig;
        this.logger = logHelperConfig.getLogger();
    }


    protected abstract void doPushLogic(Throwable throwable, String errorMsg, String errorDetail) throws Exception;


//    public String getStackTrace(Throwable throwable) {
//        return ExceptionUtils.getStackTrace(throwable);
//    }

    /**
     * 报告异常到其他系统
     */
    public Runnable getRunnable(Throwable throwable, String errorMsg, String errorDetail) {
        return () -> {
            try {
                this.doPushLogic(throwable, errorMsg, errorDetail);
            } catch (Exception e) {
                logger.warn("ErrorReporter推送失败", e);
            }
        };
    }
}
