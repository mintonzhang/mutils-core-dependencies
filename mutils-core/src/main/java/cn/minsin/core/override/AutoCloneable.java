package cn.minsin.core.override;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * 自动实现深拷贝和浅拷贝接口
 * 基于fastjson {@link JSON#toJSONString()} 实现序列化
 * 基于fastjson {@link JSON#parseObject(String, Class)} ()} 实现反序列化
 *
 * @author minton.zhang
 * @since 0.0.8.RELEASE
 */
public interface AutoCloneable<T> extends Serializable, JsonString {

    /**
     * 用于替换
     * {@link Object#clone()}
     * <p>
     * 对象
     */
    default T deepClone() {
        return JSON.parseObject(this.toJsonString(), (Type) this.getClass());
    }


    /**
     * 浅拷贝
     */
    @SuppressWarnings("unchecked")
    default T shallowClone() {
        return (T) this;
    }

}
