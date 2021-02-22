package cn.minsin.core.web.override;

import java.util.function.Function;

/**
 * @author minton.zhang
 * @since 2020/5/9 20:18
 */
public interface InnerFunction {

    @SuppressWarnings({"unchecked"})
    static <R, INPUT> R apply(Object data, Function<INPUT, R> function) {
        return function.apply((INPUT) data);
    }
}
