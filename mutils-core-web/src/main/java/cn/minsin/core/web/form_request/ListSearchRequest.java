package cn.minsin.core.web.form_request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author: minton.zhang
 * @since: 2020/4/28 17:15
 */
@Getter
@Setter
public class ListSearchRequest extends PageRequest {

	/**
	 * 搜索结束时间
	 */
	@ApiModelProperty("开始时间")
	private Date beginDate;

	/**
	 * 搜索开始时间
	 */
    @ApiModelProperty("结束时间")
    private Date endDate;

}
