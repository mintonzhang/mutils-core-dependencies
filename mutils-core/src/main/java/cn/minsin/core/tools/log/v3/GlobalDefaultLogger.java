package cn.minsin.core.tools.log.v3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link cn.minsin.core.tools.log.GlobalDefaultLogger}
 */
@Deprecated
public interface GlobalDefaultLogger {

    Logger log = LoggerFactory.getLogger("LISTENER");

}
