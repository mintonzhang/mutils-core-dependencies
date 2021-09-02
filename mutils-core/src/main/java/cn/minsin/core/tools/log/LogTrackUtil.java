package cn.minsin.core.tools.log;

import cn.minsin.core.tools.FormatStringUtil;
import cn.minsin.core.tools.SystemUtil;
import com.alibaba.fastjson.JSON;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
public class LogTrackUtil {


    private static final String OS_NAME;
    private static final String IP;
    private static final String OS_VERSION;
    private static final String OS_ARCH;
    private static final String LOG_SPLIT = " ^_^ ";
    private static final DateTimeFormatter DATE_TIME_FORMATTER;
    private static Logger log = LoggerFactory.getLogger(LogTrackUtil.class);
    private static String SYSTEM_TYPE = "UNKNOWN";
    private static String currentEnvironment = "default";
    private static List<ErrorReporter> reporter;

    /**
     * 推送错误报告时所使用的的线程池 如果不设置则使用当前线程
     */
    private static ExecutorService executorService;

    static {

        IP = SystemUtil.getIntranetIp();
        OS_NAME = SystemUtil.getProperties(SystemUtil.DefaultPropertiesKey.OS_NAME, "unknown");
        OS_VERSION = SystemUtil.getProperties(SystemUtil.DefaultPropertiesKey.OS_VERSION, "unknown");
        OS_ARCH = SystemUtil.getProperties(SystemUtil.DefaultPropertiesKey.OS_ARCH, "unknown");
        DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static Logger log() {
        return log;
    }

    public static String getSystemType() {
        return SYSTEM_TYPE;
    }

    /**
     * 设置主类和系统类型
     *
     * @param clazz      主类
     * @param systemType 系统类型 如JINGBAO
     */
    public static void setRequiredParams(Class<?> clazz, String systemType) {
        SYSTEM_TYPE = systemType;
        log = LoggerFactory.getLogger(clazz.getName()
                .concat(LOG_SPLIT)
                .concat(systemType)
                .concat(LOG_SPLIT)
                .concat(LogTrackUtil.class.getSimpleName())
        );
    }

    /**
     * 设置主类和系统类型
     *
     * @param systemType 系统类型 如JINGBAO
     */
    public static void setRequiredParams(String systemType) {
        SYSTEM_TYPE = systemType;
        log = LoggerFactory.getLogger(systemType);
    }


    public static void addErrorReport(ErrorReporter reporter) {
        LogTrackUtil.reporter = LogTrackUtil.reporter == null ? new ArrayList<>(3) : LogTrackUtil.reporter;
        LogTrackUtil.reporter.add(reporter);

    }

    public static void setExecutorService(ExecutorService executorService) {
        LogTrackUtil.executorService = executorService;
    }

    public static void setProfile(String profileName) {
        currentEnvironment = profileName;
    }

    public static void error(LogBody logBody) {
        log.error(logBody.toString());

        if (CollectionUtils.isNotEmpty(reporter)) {
            for (ErrorReporter errorReporter : reporter) {
                if (errorReporter.isExecute(currentEnvironment)) {
                    if (executorService != null) {
                        executorService.execute(() -> {
                            try {
                                errorReporter.report(logBody.errorMsg, logBody.errorDetail);
                            } catch (Exception e) {
                                log.error("错误报告上报失败 class{} ", errorReporter.getClass(), e);
                            }
                        });
                    } else {
                        try {
                            errorReporter.report(logBody.errorMsg, logBody.errorDetail);
                        } catch (Exception e) {
                            log.error("错误报告上报失败 class{} ", errorReporter.getClass(), e);
                        }
                    }


                }
            }
        }
    }

    public static void error(Throwable error) {
        error(LogBody.builder(error));
    }

    public static void error(Throwable error, @NonNull String message, Object... param) {
        String msg = FormatStringUtil.format(message, param);
        LogBody builder = LogBody.builder(error)
                .setErrorMsg(msg.concat(",rawErrorMsg:").concat(error.getMessage() == null ? "null" : error.getMessage()));
        error(builder);
    }

    public static void error(@NonNull String message, Throwable error) {
        error(error, message);
    }

    public static void error(@NonNull String message, Object... param) {
        String msg = FormatStringUtil.format(message, param);
        LogBody builder = LogBody.builder().setErrorMsg(msg);
        error(builder);
    }

    public static void warn(@NonNull String message, Object... param) {
        log.warn(message, param);
    }

    public static void warn(@NonNull String message, Throwable param) {
        log.warn(message, param);
    }

    /**
     * 记录轨迹日志 自定义方法 实际是debug
     */
    public static void trace(@NonNull String message, Object... param) {
        log.trace(message, param);
    }


    /**
     * 记录轨迹日志 自定义方法 实际是info
     */
    public static void beginTrace(@NonNull String message, Object... param) {
        log.trace("开始轨迹追踪,当前时间:".concat(formatDate()).concat(LOG_SPLIT).concat(message), param);
    }

    /**
     * 记录轨迹日志 自定义方法 实际是info
     */
    public static void errorTrace(Throwable throwable, @NonNull String message, Object... param) {
        log.error("轨迹追踪中出现异常(可在error或track中查看详情),当前时间:"
                .concat(formatDate())
                .concat(LOG_SPLIT)
                .concat("异常消息:" + throwable.getMessage())
                .concat(LOG_SPLIT)
                .concat(message), param);
    }

    /**
     * 记录轨迹日志 自定义方法 实际是info
     */
    public static void endTrace(@NonNull String message, Object... param) {
        log.trace("结束轨迹追踪,当前时间:".concat(formatDate()).concat(LOG_SPLIT).concat(message), param);
    }

    public static void info(@NonNull String message, Object... param) {
        log.info(message, param);
    }

    public static String parserException(Throwable e) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (PrintStream printStream = new PrintStream(byteArrayOutputStream)) {
            e.printStackTrace(printStream);
        } catch (Exception e1) {
            return e1.getMessage();
        }
        return byteArrayOutputStream.toString().replace("\r\n", LOG_SPLIT + "\r\n");
    }

    public static String formatDate() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    public static class LogBody {

        /**
         * 异常码
         */
        private String errorCode = "10000";
        /**
         * 当前线程名称
         */
        private String threadName = Thread.currentThread().getName();
        /**
         * 异常详细
         */
        private String errorMsg;
        /**
         * 异常详情
         */
        private String errorDetail;
        /**
         * 入参参数
         */
        @Setter(AccessLevel.NONE)
        private HashMap<String, Object> inputParam;

        public static LogBody builder(Throwable throwable) {
            return new LogBody()
                    .setErrorMsg(throwable.getMessage())
                    .setErrorDetail(parserException(throwable));
        }

        public static LogBody builder() {
            return new LogBody();
        }

        /**
         * 添加异常参数
         *
         * @param key   参数名称
         * @param value 参数值
         */
        public LogBody addParam(String key, Object value) {
            this.inputParam = inputParam == null ? new HashMap<>(3) : inputParam;
            this.inputParam.put(key, value);
            return this;
        }

        @Override
        public String toString() {
            return "\r\n" +
                    "{\r\n\"ip\":\"" + IP + "\"," +
                    "\r\n\"systemType\": \"" + SYSTEM_TYPE + "\"," +
                    "\r\n\"threadName\": \"" + threadName + "\"," +
                    "\r\n\"osName\": \"" + OS_NAME + "\"," +
                    "\r\n\"osVersion\": \"" + OS_VERSION + "\"," +
                    "\r\n\"osArch\": \"" + OS_ARCH + "\"," +
                    "\r\n\"datetime\": \"" + formatDate() + "\"," +
                    "\r\n\"inputParam\": \"" + JSON.toJSONString(this.getInputParam()) + "\"," +
                    "\r\n\"errorCode\": \"" + this.getErrorCode() + "\"," +
                    "\r\n\"errorMsg\": \"" + this.getErrorMsg() + "\"," +
                    "\r\n\"errorDetail\": \"" + this.getErrorDetail() + "\"" +
                    "\r\n}";

        }
    }

    /**
     * 消息报告对象
     */
    public static abstract class ErrorReporter {
        protected final String OS_NAME = LogTrackUtil.OS_NAME;
        protected final String IP = LogTrackUtil.IP;
        protected final String OS_VERSION = LogTrackUtil.OS_VERSION;
        protected final String OS_ARCH = LogTrackUtil.OS_ARCH;

        /**
         * 报告异常到其他系统
         */
        public abstract void report(String errorMsg, String errorDetail) throws Exception;

        /**
         * 判断是否需要执行
         */
        public abstract boolean isExecute(String currentEnvironment);
    }
}
