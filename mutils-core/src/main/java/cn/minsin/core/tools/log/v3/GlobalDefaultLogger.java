package cn.minsin.core.tools.log.v3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 全局默认日志记录器 仅记录日志
 */
public interface GlobalDefaultLogger {

    Logger log = LoggerFactory.getLogger("LISTENER");

}
