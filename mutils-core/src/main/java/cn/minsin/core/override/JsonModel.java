package cn.minsin.core.override;

/**
 * 继承该类之后 toString方法将转换为json字符串
 * @author: minton.zhang
 * @since: 2020/1/21 13:26
 */
public abstract class JsonModel implements JsonString {

    @Override
    public String toString() {
        return this.toJsonString();
    }
}
