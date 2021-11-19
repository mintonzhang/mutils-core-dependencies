package cn.minsin.core.tools.log.common.reporeies;

import cn.minsin.core.tools.StringUtil;
import cn.minsin.core.tools.log.common.BaseJsonObjectReportRequest;
import cn.minsin.core.tools.log.common.LoggerHelperConfig;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

/**
 * @author minton.zhang
 * @since 2021/11/8 10:28
 */
public abstract class BaseErrorReporter {
    protected LoggerHelperConfig logHelperConfig;

    protected Logger logger;


    public void setLoggerHelperConfig(LoggerHelperConfig logHelperConfig) {
        this.logHelperConfig = logHelperConfig;
        this.logger = logHelperConfig.getLogger();
    }

    protected abstract void doPushLogic(Throwable throwable, String errorMsg) throws Exception;

    protected void doPushLogicForJsonData(BaseJsonObjectReportRequest jsonObject) throws Exception {
        //默认不实现json格式的推送
        logger.warn("{}#doPushLogicForJsonData,not support", this.getClass());
    }


    /**
     * 解析详细异常栈 如果需要的话
     */
    protected String getStackTrace(Throwable throwable) {
        return throwable == null ? StringUtil.EMPTY : ExceptionUtils.getStackTrace(throwable);
    }

    /**
     * 报告异常到其他系统
     */
    public Runnable getRunnable(Throwable throwable, String errorMsg) {
        return () -> {
            try {
                this.doPushLogic(throwable, errorMsg);
            } catch (Exception e) {
                logger.warn("ErrorReporter推送失败", e);
            }
        };
    }

    /**
     * 报告异常到其他系统
     */
    public Runnable getRunnable(BaseJsonObjectReportRequest request) {
        return () -> {
            try {
                this.doPushLogicForJsonData(request);
            } catch (Exception e) {
                logger.warn("ErrorReporter推送失败", e);
            }
        };
    }
}
