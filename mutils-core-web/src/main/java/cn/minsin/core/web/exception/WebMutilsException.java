package cn.minsin.core.web.exception;

import cn.minsin.core.exception.MutilsException;
import cn.minsin.core.web.result.DefaultResultOptions;

/**
 * @author: minton.zhang
 * @since: 2019/11/7 19:05
 */
public class WebMutilsException extends MutilsException {


    @Override
    public String getMessage() {
        return super.getCause() != null ? DefaultResultOptions.EXCEPTION.getMsg() : super.getMessage();
    }
}
