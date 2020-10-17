package cn.minsin.core.web.form_request;

import cn.minsin.core.web.override.ConvertTo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 保存或修改 模板
 *
 * @author: minton.zhang
 * @since: 2020/4/28 16:54
 */
@Getter
@Setter
public class SaveRequest<T extends Serializable, P> extends OneParamRequest<T> implements ConvertTo<P> {


    @Override
    public <R> R convertTo(Function<P, R> convertFunction) {
        return InnerFunction.apply(this, convertFunction);
    }
}
