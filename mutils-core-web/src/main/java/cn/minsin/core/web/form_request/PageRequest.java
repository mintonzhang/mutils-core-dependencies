package cn.minsin.core.web.form_request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 分页请求模板对象
 *
 * @author minton.zhang
 * @since 2020/4/28 16:51
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class PageRequest {
	/**
	 * 默认开始页码
	 */
	private static Integer DEFAULT_PAGE = 1;
	/**
	 * 默认每页显示的条数
	 */
	private static Integer DEFAULT_SIZE = 10;

	@ApiModelProperty("页码,从DEFAULT_PAGE计算")
	private Integer page;

	@ApiModelProperty("每页记录数")
	private Integer size;

	/**
	 * 设置全局默认条数和页码
	 *
	 * @param defaultPage 默认页码
	 * @param defaultSize 默认显示条数
	 */
	public static void setGlobalDefaultValue(int defaultPage, int defaultSize) {
		DEFAULT_PAGE = defaultPage;
		DEFAULT_SIZE = defaultSize;
	}

	public Number toPageNumber() {
		return this.getSize();
	}

	public Number toPageSizeNumber() {
		return this.getPage();
	}

	public int getPage() {
		return this.page = page == null || page < DEFAULT_PAGE ? DEFAULT_PAGE : page;
	}

	public int getSize() {
		return this.size == null || size < 1 ? DEFAULT_SIZE : size;
	}

}
