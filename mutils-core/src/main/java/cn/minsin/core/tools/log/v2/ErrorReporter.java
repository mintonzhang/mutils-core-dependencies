package cn.minsin.core.tools.log.v2;

/**
 * @author minton.zhang
 * @since 2021/11/8 10:28
 */
public abstract class ErrorReporter {
    protected LoggerHelperConfig logHelperConfig;


    public void setLoggerHelperConfig(LoggerHelperConfig logHelperConfig) {
        this.logHelperConfig = logHelperConfig;
    }

    /**
     * 报告异常到其他系统
     */
    public abstract void report(Throwable throwable, String errorMsg, String errorDetail) throws Exception;

}
