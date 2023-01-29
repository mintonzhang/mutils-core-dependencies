package cn.minsin.core.session;

import cn.minsin.core.constant.SuppressWarningsTypeConstant;
import cn.minsin.core.thread.override.RunnablePlus;
import io.netty.util.concurrent.FastThreadLocal;

import java.util.concurrent.ExecutorService;

/**
 * @author minton.zhang
 * @since 2021/7/29 16:46
 */
@SuppressWarnings(SuppressWarningsTypeConstant.UNCHECKED)
public interface SessionContext {

    FastThreadLocal<SessionUser> MAP = new FastThreadLocal<>();


    /**
     * 获取线程内数据
     */
    static <T extends SessionUser> T get() {
        try {
            return (T) MAP.get();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 清空线程内数据
     */
    static void remove() {
        MAP.remove();
    }

    /**
     * 设置线程数据
     */
    static void set(SessionUser serializable) {
        MAP.set(serializable);
    }


    /**
     * 执行异步任务
     */
    static void execute(ExecutorService executorService, Runnable runnable) {
        //暂存数据
        executorService.execute(wrapSession(runnable));
    }

    /**
     * wrapSession的Runnable
     */
    static Runnable wrapSession(Runnable runnable) {
        if (runnable instanceof RunnablePlus) {
            return runnable;
        }
        //暂存数据
        return new SessionRunnable(get(), runnable);

    }


}
