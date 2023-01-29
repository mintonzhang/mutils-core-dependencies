package cn.minsin.core.web.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author minton.zhang
 * @since 2020/6/11 14:28
 */
@Getter
@Setter
public class BusinessUnsupportedException extends RuntimeException {


    public BusinessUnsupportedException(String message) {
        super(message);
    }

    public BusinessUnsupportedException(String message, Throwable cause) {
        super(message, cause);
    }

}
