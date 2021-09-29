package cn.minsin.core.tools.function;

/**
 * @author minton.zhang
 * @since 2020/10/27 12:53
 */
@FunctionalInterface
public interface NonFunction<T> {

    /**
     * 不需要接收任何参数
     */
    T apply();
}
