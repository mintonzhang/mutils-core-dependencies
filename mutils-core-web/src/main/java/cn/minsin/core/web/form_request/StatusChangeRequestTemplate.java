package cn.minsin.core.web.form_request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 状态请求模板
 *
 * @author: minton.zhang
 * @since: 2020/4/28 17:06
 */
@Getter
@Setter
public class StatusChangeRequestTemplate<ID_TYPE extends Serializable, STATUS_TYPE> extends DeleteRequestTemplate<ID_TYPE> {

    /**
     * 状态使用的类型
     */
    @ApiModelProperty("状态")
    private STATUS_TYPE status;

}
