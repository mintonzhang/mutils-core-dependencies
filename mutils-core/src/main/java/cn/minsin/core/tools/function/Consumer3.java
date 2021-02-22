package cn.minsin.core.tools.function;

/**
 * @author minton.zhang
 * @since 2020/5/20 16:32
 */
@FunctionalInterface
public interface Consumer3<P1, P2, P3> {

    /**
     * 四目运算符
     *
     * @param p1 第一个入参
     * @param p2 第二个入参
     * @param p3 第三个入参
     */
    void accept(P1 p1, P2 p2, P3 p3);
}
