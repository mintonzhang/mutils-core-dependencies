package cn.minsin.core.tools.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xml配置打印格式 建议
 * [%d{yyyy-MM-dd HH:mm:ss.SSS}] [%thread] [%level] [%logger{50}] [%file:%line] --- %msg%n
 *
 * @author minsin/mintonzhang@163.com
 * @since 2022/4/25
 */
public interface GlobalBuriedPointLogger {

    Logger log = LoggerFactory.getLogger("BURIED_POINT");
}
