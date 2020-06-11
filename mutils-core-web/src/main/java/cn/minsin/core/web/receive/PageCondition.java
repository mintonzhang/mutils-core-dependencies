package cn.minsin.core.web.receive;

import cn.minsin.core.tools.NumberUtil;
import cn.minsin.core.web.override.ConvertToPage;
import cn.minsin.core.web.override.InnerFunction;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.function.Function;

/**
 * @Author: minton.zhang
 * @Date: 2019/8/2 11:09
 */
@Getter
@Accessors(chain = true)
public class PageCondition<T> implements ConvertToPage<T> {


    @ApiModelProperty("页码 默认1")
    private int page = 1;

    @ApiModelProperty("显示条数 默认10")
    private int pageSize = 10;

    public void setPage(String page) {
        int i = NumberUtil.toInt(page, 1);
        this.page = Math.max(i, 1);
    }

    public void setPageSize(String pageSize) {
        int i = NumberUtil.toInt(pageSize, 10);
        this.pageSize = i < 1 ? 10 : i;
    }

    @Override
    public <R> R convertToPage(Function<T, R> convertFunction) {
        return InnerFunction.apply(this, convertFunction);
    }

}
