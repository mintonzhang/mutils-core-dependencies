package cn.minsin.core.tools.log.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author minton.zhang
 * @since 2021/11/8 10:39
 */
@Getter
@Setter
@Accessors(chain = true)
public class DefaultLoggerBodyFormatter implements BaseLoggerBodyFormatter {
//
//
//    /**
//     * 异常码
//     */
//    private String errorCode = "10000";
//    /**
//     * 当前线程名称
//     */
//    private String threadName = Thread.currentThread().getName();
//    /**
//     * 异常详细
//     */
//    private String errorMsg;
//    /**
//     * 异常详情
//     */
//    private String errorDetail;
//    /**
//     * 入参参数
//     */
//    @Setter(AccessLevel.NONE)
//    private HashMap<String, Object> inputParam;

    @Override
    public String getErrorMessage(Throwable exception, LoggerHelperConfig config) {
        return BaseLoggerBodyFormatter.getStackTrace(exception);
    }

//    /**
//     * 添加异常参数
//     *
//     * @param key   参数名称
//     * @param value 参数值
//     */
//    public DefaultLoggerBodyFormatter addParam(String key, Object value) {
//        this.inputParam = inputParam == null ? new HashMap<>(3) : inputParam;
//        this.inputParam.put(key, value);
//        return this;
//    }
//
//    @Override
//    public String toString() {
//        return "\r\n" +
//                "{\r\n\"ip\":\"" + IP + "\"," +
//                "\r\n\"systemType\": \"" + SYSTEM_TYPE + "\"," +
//                "\r\n\"threadName\": \"" + threadName + "\"," +
//                "\r\n\"osName\": \"" + OS_NAME + "\"," +
//                "\r\n\"osVersion\": \"" + OS_VERSION + "\"," +
//                "\r\n\"osArch\": \"" + OS_ARCH + "\"," +
//                "\r\n\"datetime\": \"" + formatDate() + "\"," +
//                "\r\n\"inputParam\": \"" + JSON.toJSONString(this.getInputParam()) + "\"," +
//                "\r\n\"errorCode\": \"" + this.getErrorCode() + "\"," +
//                "\r\n\"errorMsg\": \"" + this.getErrorMsg() + "\"," +
//                "\r\n\"errorDetail\": \"" + this.getErrorDetail() + "\"" +
//                "\r\n}";
//
//    }
}
