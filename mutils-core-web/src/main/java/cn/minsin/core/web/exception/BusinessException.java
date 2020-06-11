package cn.minsin.core.web.exception;

import lombok.NonNull;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author: minton.zhang
 * @since: 2020/6/11 14:28
 */
public class BusinessException extends RuntimeException {

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * 抛出业务异常
     *
     * @param condition true时抛出
     * @param message   异常消息
     * @param param     异常参数 可不传
     */
    public static void throwException(boolean condition, @NonNull String message, Object... param) {
        if (condition) {
            if (param != null && param.length > 0) {
                FormattingTuple formattingTuple = MessageFormatter.arrayFormat(message, param);
                throw new BusinessException(formattingTuple.getMessage());
            }
            throw new BusinessException(message);
        }
    }
}
