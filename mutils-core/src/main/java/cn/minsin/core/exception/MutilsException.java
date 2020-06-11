package cn.minsin.core.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 框架内发生一般异常时抛出
 *
 * @author minsin
 */
@Slf4j
public class MutilsException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -1254579703396031354L;

    public MutilsException(String msg) {
        super(msg);
    }

    public MutilsException() {
    }

    public MutilsException(Throwable cause, String msg) {
        super(msg, cause);
    }

    public MutilsException(Throwable cause) {
        super(cause);
    }


    /**
     * condition为true时,抛出异常
     *
     * @param trueCondition true
     * @param message 提示消息
     */
    public static void throwException(boolean trueCondition, String message) {
        if (trueCondition) {
            throw new MutilsException(message);
        }
    }

    /**
     * condition为true时,抛出异常
     *
     * @param trueCondition true
     */
    public static void throwException(boolean trueCondition) {
        if (trueCondition) {
            throw new MutilsException();
        }
    }


    /**
     * 为空时抛出异常
     *
     * @param object 对象
     * @param message 为空时的提示消息
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new MutilsException(message);
        }
    }
}
