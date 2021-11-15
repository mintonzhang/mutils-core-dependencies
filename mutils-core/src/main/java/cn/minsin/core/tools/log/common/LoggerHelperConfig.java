package cn.minsin.core.tools.log.common;

import cn.minsin.core.tools.log.common.reporeies.BaseErrorReporter;
import cn.minsin.core.tools.log.v2.LoggerTrackHelper;
import cn.minsin.core.tools.log.v3.LoggerPusherHelper;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author minton.zhang
 * @since 2021/11/8 10:23
 */
@Getter
public class LoggerHelperConfig {

    private final Logger logger;
    @Setter
    private String systemName;
    @Setter
    private String currentProfile;

    private final List<BaseErrorReporter> baseErrorReporters = new ArrayList<>(3);
    /**
     * 推送错误报告时所使用的的线程池 如果不设置则使用当前线程
     */
    private final ExecutorService executorService;
    @Setter
    private BaseLoggerBodyFormatter loggerBodyFormatter = new DefaultLoggerBodyFormatter();

    public LoggerHelperConfig(Logger logger, ExecutorService executorService) {
        this.logger = logger;
        this.executorService = executorService;
    }

    public LoggerHelperConfig addErrorReport(BaseErrorReporter baseErrorReporter) {
        baseErrorReporter.setLoggerHelperConfig(this);
        baseErrorReporters.add(baseErrorReporter);
        return this;
    }


    public void initLoggerHelper() {
        LoggerTrackHelper.logger = logger;
        LoggerTrackHelper.DEFAULT_LOGGER_CONFIG = this;
    }

    public void initLoggerPusherHelper() {
        LoggerPusherHelper.setDefaultLoggerConfig(this);
    }

}
