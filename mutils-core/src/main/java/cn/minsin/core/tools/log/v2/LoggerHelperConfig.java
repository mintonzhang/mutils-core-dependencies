package cn.minsin.core.tools.log.v2;

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
    private final String systemName;
    private final String currentProfile;
    private final List<ErrorReporter> errorReporters = new ArrayList<>(3);
    /**
     * 推送错误报告时所使用的的线程池 如果不设置则使用当前线程
     */
    private final ExecutorService executorService;
    @Setter
    private BaseLoggerBodyFormatter loggerBodyFormatter = new DefaultLoggerBodyFormatter();

    public LoggerHelperConfig(Logger logger, String profile, ExecutorService executorService) {
        this.logger = logger;
        this.systemName = profile;
        this.currentProfile = profile;
        this.executorService = executorService;
    }

    public LoggerHelperConfig addErrorReport(ErrorReporter errorReporter) {
        errorReporter.setLoggerHelperConfig(this);
        errorReporters.add(errorReporter);
        return this;
    }


    public void initLoggerHelper() {
        LoggerTrackHelper.logger = logger;
        LoggerTrackHelper.loggerHelperConfig = this;
    }

}
