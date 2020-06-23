package cn.minsin.core.web.form_request;

import java.util.function.Function;

/**
 * @author: minton.zhang
 * @since: 2020/4/28 19:15
 */
public class InnerFunction {

    @SuppressWarnings("unchecked")
    public static <T, P> T apply(Object o, Function<P, T> function) {
        return function.apply((P) o);
    }
}
