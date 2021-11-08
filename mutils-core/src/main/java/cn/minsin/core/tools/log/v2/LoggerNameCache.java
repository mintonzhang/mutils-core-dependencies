package cn.minsin.core.tools.log.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 规范日志
 *
 * @author minton.zhang
 * @since 2021/11/1 10:18
 */
@FunctionalInterface
public interface LoggerNameCache {

    Map<String, Logger> LOGGER_MAP = new ConcurrentHashMap<>();

    /**
     * 获取日志名称
     */
    String getLogName();

    default Logger getLogger() {
        return LOGGER_MAP.computeIfAbsent(this.getLogName(), k -> this.createLogger());
    }


    default Logger createLogger() {
        synchronized (LOGGER_MAP) {
            return LoggerFactory.getLogger(this.getLogName());
        }
    }
}
