package cn.minsin.core.override;

/**
 * @author minton.zhang
 * @since 2021/9/15 10:39
 */
@FunctionalInterface
public interface AsyncCallback<R> {

    /**
     * 当线程开始执行时
     *
     * @param thread 执行的线程
     */
    default void whenRun(Thread thread) {

    }

    /**
     * 当线程执行完成时
     *
     * @param value 执行的结果值
     */
    void whenComplete(R value);

    /**
     * 当线程执行异常时
     *
     * @param throwable 所发生的异常
     */
    default void whenThrowException(Throwable throwable) {
        throwable.printStackTrace();
    }


}
