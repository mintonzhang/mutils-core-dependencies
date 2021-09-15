package cn.minsin.core.override;

/**
 * @author minton.zhang
 * @since 2021/9/15 10:23
 */
public interface MatchableHandler<M, IN, OUT> extends Matchable<M> {

    /**
     * 内部执行匹配逻辑
     *
     * @param match 需要匹配的参数
     * @param param 入参
     * @return 返回值
     */
    OUT innerHandle(M match, IN param) throws Exception;

    /**
     * 外部调用的方法
     *
     * @param match 匹配方法
     * @param param 入参
     * @return 执行结果返回
     */
    default OUT handle(M match, IN param) throws Exception {
        if (this.match(match)) {
            return this.innerHandle(match, param);
        }
        throw new UnsupportedOperationException("非法调用");
    }
}
