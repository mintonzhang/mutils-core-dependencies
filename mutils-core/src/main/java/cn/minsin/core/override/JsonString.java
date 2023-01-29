package cn.minsin.core.override;

        import com.alibaba.fastjson2.JSON;

/**
 * 继承或实现该接口，将提供toJsonString方法
 *
 * @author minton.zhang
 * @since 2020/1/21 13:26
 */
public interface JsonString {
    /**
     * 将当前对象转换成JSON字符串
     * 使用fastJSON中的{@link JSON#toJSONString(Object)} )}
     */
    default String toJsonString() {
        return JSON.toJSONString(this);
    }
}
