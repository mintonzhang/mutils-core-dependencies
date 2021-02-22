package cn.minsin.core.web.form_request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 唯一标识模板
 *
 * @author minton.zhang
 * @since 2020/4/15 13:58
 */
@Getter
@Setter
public class OneParamRequest<T extends Serializable> {

    /**
     * 唯一标识
     */
    @ApiModelProperty("唯一标识")
    private T id;

}
