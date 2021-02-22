package cn.minsin.core.web.form_request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 删除请求模板
 *
 * @author minton.zhang
 * @since 2020/4/28 17:06
 */
@Getter
@Setter
public class DeleteRequest<T extends Serializable> extends OneParamRequest<T> {

    /**
     * 备注 根据自己业务判断是否使用
     */
    @ApiModelProperty("备注")
    private String remark;
}
