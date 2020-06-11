package cn.minsin.core.tools.date;


/**
 * 可使用lamuda表达式 eg: ()->"yyyy-MM-dd"
 *
 * @author minton.zhang
 * @date 2019年6月12日
 */
@FunctionalInterface
public interface DateFormat {

    /**
     * 获取格式化
     */
    String getFormat();

}
