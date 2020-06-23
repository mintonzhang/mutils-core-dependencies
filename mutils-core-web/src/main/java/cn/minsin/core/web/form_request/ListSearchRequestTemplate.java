package cn.minsin.core.web.form_request;

import cn.minsin.core.web.override.ConvertToSearchCondition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.function.Function;

/**
 * @author: minton.zhang
 * @since: 2020/4/28 17:15
 */
@Getter
@Setter
public class ListSearchRequestTemplate<R> extends PageRequestTemplate<R> implements ConvertToSearchCondition<R> {

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

    /**
     * 提供一个默认的搜索关键词
     */
    private String searchKey;


    /**
     * 将查询转换为查询语句。
     * <pre>
     *    toSearchCondition(e->{
     *           QueryWrapper query =new QueryWrapper();
     *           query.eq("name",searchKey);
     *           return query;
     *       })
     *
     * </pre>
     *
     * @return
     */
    @Override
    public <N> N convertToSearchCondition(Function<R, N> convertFunction) {
        return InnerFunction.apply(this, convertFunction);
    }
}
