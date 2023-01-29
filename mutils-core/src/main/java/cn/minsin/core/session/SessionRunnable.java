package cn.minsin.core.session;

import cn.minsin.core.thread.override.RunnablePlus;
import cn.minsin.core.tools.log.GlobalDefaultLogger;
import lombok.RequiredArgsConstructor;

/**
 * @author minsin/mintonzhang@163.com
 * @since 2022/5/15
 */
@RequiredArgsConstructor
public class SessionRunnable implements RunnablePlus {


    protected final SessionUser user;

    protected final Runnable runnable;

    @Override
    public void beforeRunInit() {
        SessionContext.remove();
        SessionContext.set(user);
    }

    @Override
    public void doFinal() {
        SessionContext.remove();
    }

    @Override
    public void doRun() {
        runnable.run();
    }

    @Override
    public void whenRunHasError(Throwable e) {
        if (runnable instanceof RunnablePlus) {
            ((RunnablePlus) runnable).whenRunHasError(e);
        } else {
            GlobalDefaultLogger.log.error("任务执行失败", e);
        }
    }
}
