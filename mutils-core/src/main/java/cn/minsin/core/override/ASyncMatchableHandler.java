package cn.minsin.core.override;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步匹配执行
 *
 * @author minton.zhang
 * @since 2021/9/15 10:23
 */
public interface ASyncMatchableHandler<M, IN, OUT> extends MatchableHandler<M, IN, OUT> {


    /**
     * 异步调用
     *
     * @param match    匹配函数
     * @param param    入参
     * @param callback 回调
     */
    default void asyncHandle(M match, IN param, AsyncCallback<OUT> callback) {
        Runnable runnable = () -> {
            try {
                callback.whenRun(Thread.currentThread());
                OUT handle = this.handle(match, param);
                callback.whenComplete(handle);
            } catch (Exception e) {
                callback.whenThrowException(e);
            }
        };
        this.executeRunnable(runnable);
    }

    /**
     * 执行线程
     * 默认使用{@link Thread}
     * 参考线程池{@link Executors#newSingleThreadExecutor()}
     * 参考线程池{@link Executors#newCachedThreadPool()}
     * 参考线程池{@link ThreadPoolExecutor}
     */
    default void executeRunnable(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName("asyncMatchableHandler-default");
        thread.start();
    }
}
