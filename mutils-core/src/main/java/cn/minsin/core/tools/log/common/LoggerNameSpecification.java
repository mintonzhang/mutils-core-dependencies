package cn.minsin.core.tools.log.common;

import cn.minsin.core.tools.log.v3.LoggerPusherHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author minton.zhang
 * @since 2021/11/15 17:14
 */
public interface LoggerNameSpecification {


    /**
     * logger缓存
     */
    Map<String, Logger> LOGGER_MAP = new ConcurrentHashMap<>();


    String getLoggerName();

    default LoggerPusherHelper getLoggerPusherHelper() {
        return LoggerPusherHelper.of(this.getLogger());
    }

    default Logger getLogger() {
        return LOGGER_MAP.computeIfAbsent(this.getLoggerName(), k -> this.createLogger());
    }

    default Logger createLogger() {
        synchronized (LOGGER_MAP) {
            return LoggerFactory.getLogger(this.getLoggerName());
        }
    }
}
