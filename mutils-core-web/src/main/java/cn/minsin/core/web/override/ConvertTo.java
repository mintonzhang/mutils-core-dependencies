package cn.minsin.core.web.override;

import org.springframework.beans.BeanUtils;

import java.util.function.Function;

/**
 * @author: minton.zhang
 * @since: 2020/6/23 18:01
 */
public interface ConvertTo<INPUT> {

    <R> R convertTo(Function<INPUT, R> convertFunction);

    /**
     * 使用spring的bean util进行复制
     */
    default <R> R convertTo(R target) {
        BeanUtils.copyProperties(this, target);
        return target;
    }
}
