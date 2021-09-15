package cn.minsin.core.override;

/**
 * @author minton.zhang
 * @since 2021/9/15 10:23
 */
@FunctionalInterface
public interface Matchable<M> {

    /**
     * 匹配方法
     */
    boolean match(M data);
}
