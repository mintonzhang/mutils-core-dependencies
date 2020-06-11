package cn.minsin.core.web.receive;

import cn.minsin.core.tools.date.DateUtil;
import cn.minsin.core.tools.date.DefaultDateFormat;
import cn.minsin.core.web.override.ConvertToSearchCondition;
import cn.minsin.core.web.override.InnerFunction;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.function.Function;

/**
 * 用于表格时间段搜索
 *
 * @Author: minton.zhang
 * @since: 0.0.8.RELEASE
 */
@Getter
@Accessors(chain = true)
public class DateCondition<T> implements ConvertToSearchCondition<T> {

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间 yyyy-MM-dd")
    private Date beginDate;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间  yyyy-MM-dd")
    private Date endDate;

    public DateCondition<T> setBeginDate(String beginDate) {
        this.beginDate = beginDate == null ? null : DateUtil.getBeginOfDay(beginDate, DefaultDateFormat.yyyy_MM_dd);
        return this;
    }

    public DateCondition<T> setEndDate(String endDate) {
        this.endDate = endDate == null ? null : DateUtil.getEndOfDay(endDate, DefaultDateFormat.yyyy_MM_dd);
        return this;
    }

    @Override
    public <R> R convertToSearchCondition(Function<T, R> convertFunction) {
        return InnerFunction.apply(this, convertFunction);
    }


}
