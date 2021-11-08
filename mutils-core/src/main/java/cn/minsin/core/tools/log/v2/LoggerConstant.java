package cn.minsin.core.tools.log.v2;

import cn.minsin.core.tools.SystemUtil;

import java.time.format.DateTimeFormatter;

/**
 * @author minton.zhang
 * @since 2021/11/8 10:53
 */
public abstract class LoggerConstant {
    public static String OS_NAME;
    public static String IP;
    public static String OS_VERSION;
    public static String OS_ARCH;
    public static DateTimeFormatter DATE_TIME_FORMATTER;
    public static String LOG_SPLIT = "^_^";

    static {
        IP = SystemUtil.getIntranetIp();
        OS_NAME = SystemUtil.getProperties(SystemUtil.DefaultPropertiesKey.OS_NAME, "unknown");
        OS_VERSION = SystemUtil.getProperties(SystemUtil.DefaultPropertiesKey.OS_VERSION, "unknown");
        OS_ARCH = SystemUtil.getProperties(SystemUtil.DefaultPropertiesKey.OS_ARCH, "unknown");
        DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    }
}
