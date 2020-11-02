package cn.minsin.core.web.form_request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 分页请求模板对象
 *
 * @author: minton.zhang
 * @since: 2020/4/28 16:51
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PageRequest {
	/**
	 * 默认开始页码
	 */
	private static Integer DEFAULT_PAGE = 1;
	/**
	 * 默认每页显示的条数
	 */
	private static Integer DEFAULT_SIZE = 10;

	@NotNull
    @ApiModelProperty("页码,从DEFAULT_PAGE计算")
    private int page;

    @NotNull
    @ApiModelProperty("每页记录数")
    private int size;

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


    public void setPage(Integer page) {
        this.page = page == null || page < DEFAULT_PAGE ? DEFAULT_PAGE : page;
    }

    public void setSize(Integer size) {
        this.size = size == null || size < 1 ? DEFAULT_SIZE : size;
    }


    /**
     * long类型的size
     */
    public long toLongSize() {
        return this.getSize();
    }

    /**
     * long内息的page
     */
    public long toLongPage() {
        return this.getPage();
    }

}
