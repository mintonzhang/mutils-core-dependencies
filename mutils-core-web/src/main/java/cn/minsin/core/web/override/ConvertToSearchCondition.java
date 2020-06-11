package cn.minsin.core.web.override;

import java.util.function.Function;

/**
 * @author: minton.zhang
 * @since: 2020/5/9 20:15
 */
@FunctionalInterface
public interface ConvertToSearchCondition<INPUT> {

    <R> R convertToSearchCondition(Function<INPUT, R> convertFunction);
}
