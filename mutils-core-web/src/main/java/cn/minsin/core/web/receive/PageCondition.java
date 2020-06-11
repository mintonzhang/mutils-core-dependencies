package cn.minsin.core.web.receive;

import cn.minsin.core.web.override.ConvertToPage;
import cn.minsin.core.web.override.InnerFunction;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.function.Function;

/**
 * @Author: minton.zhang
 * @Date: 2019/8/2 11:09
 */
@Setter
@Accessors(chain = true)
public class PageCondition<T> implements ConvertToPage<T> {

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
    private Integer page;
    @NotNull
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

    public Integer getPage() {
        return page == null || page < DEFAULT_PAGE ? DEFAULT_PAGE : page;
    }

    public Integer getSize() {
        return size == null || size < 1 ? DEFAULT_SIZE : size;
    }

    /**
     * long类型的size
     */
    public long toLongSize() {
        return this.getSize().longValue();
    }

    /**
     * long内息的page
     */
    public long toLongPage() {
        return this.getPage().longValue();
    }

    @Override
    public <R> R convertToPage(Function<T, R> convertFunction) {
        return InnerFunction.apply(this, convertFunction);
    }

}
