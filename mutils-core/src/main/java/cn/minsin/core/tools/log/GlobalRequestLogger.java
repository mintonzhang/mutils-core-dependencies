package cn.minsin.core.tools.log;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * xml配置打印格式 建议
 * [%d{yyyy-MM-dd HH:mm:ss.SSS}] [%thread] [%level] [%logger{50}] [%file:%line] --- %msg%n
 *
 * @author minsin/mintonzhang@163.com
 * @since 2022/4/25
 */
public interface GlobalRequestLogger {

    String EMPTY_JSON = "{}";

    Logger log = LoggerFactory.getLogger("HTTP_REQUEST_LOG");

    String FEEDBACK_MESSAGE = "feedbackMessage";

    /**
     * 添加日志 可修改该方法切换日志打印器
     *
     * @param level      打印级别
     * @param jsonString 消息
     * @param throwable  参数
     */
    static void printLogging(Level level, String jsonString, Throwable throwable) {

        switch (level) {
            case ERROR:
                if (throwable == null) {
                    log.error("{}", jsonString);
                } else {
                    log.error("{}", jsonString, throwable);
                }
                break;
            case WARN:
                if (throwable == null) {
                    log.warn("{}", jsonString);
                } else {
                    log.warn("{}", jsonString, throwable);
                }
                break;
            case INFO:
                if (throwable == null) {
                    log.info("{}", jsonString);
                } else {
                    log.info("{}", jsonString, throwable);
                }
                break;
        }
    }

    static void error(String message, Consumer<LinkedHashMap<String, Serializable>> consumer) {
        LinkedHashMap<String, Serializable> map = new LinkedHashMap<>();
        consumer.accept(map);
        map.put(FEEDBACK_MESSAGE, message);
        printLogging(Level.ERROR, JSON.toJSONString(map), null);
    }

    static void error(String message, Throwable throwable, Consumer<LinkedHashMap<String, Serializable>> consumer) {
        LinkedHashMap<String, Serializable> map = new LinkedHashMap<>();
        consumer.accept(map);
        map.put(FEEDBACK_MESSAGE, message);
        printLogging(Level.ERROR, JSON.toJSONString(map), throwable);
    }


    //********************************2022年06月16日14:06:48 添加****************************************//

    static void warn(String message, Consumer<LinkedHashMap<String, Serializable>> consumer) {
        LinkedHashMap<String, Serializable> map = new LinkedHashMap<>();
        consumer.accept(map);
        map.put(FEEDBACK_MESSAGE, message);
        printLogging(Level.WARN, JSON.toJSONString(map), null);
    }

    static void warn(String message, Throwable throwable, Consumer<LinkedHashMap<String, Serializable>> consumer) {
        LinkedHashMap<String, Serializable> map = new LinkedHashMap<>();
        consumer.accept(map);
        map.put(FEEDBACK_MESSAGE, message);
        printLogging(Level.WARN, JSON.toJSONString(map), throwable);
    }

    default void printLog(Level level, Map<String, Serializable> jsonMap, Throwable throwable) {
        String jsonString = MapUtils.isEmpty(jsonMap) ? EMPTY_JSON : JSON.toJSONString(jsonMap);
        printLogging(level, jsonString, throwable);
    }

    default void printLog(Level level, Object object, Throwable throwable) {
        String jsonString = object == null ? EMPTY_JSON : JSON.toJSONString(object);
        printLogging(level, jsonString, throwable);
    }

    //********************************2022年06月16日14:06:48 添加****************************************//

}
