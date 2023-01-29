package cn.minsin.core.thread.pool;

import cn.minsin.core.tools.log.GlobalDefaultLogger;
import org.apache.commons.lang3.ClassUtils;

import java.util.concurrent.ThreadPoolExecutor;

public class CallerRunsThenPrintPolicy extends ThreadPoolExecutor.CallerRunsPolicy implements GlobalDefaultLogger {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        int corePoolSize = e.getCorePoolSize();
        int maximumPoolSize = e.getMaximumPoolSize();
        int activeCount = e.getActiveCount();
        log.error("线程池不够使用 本次将使用[{}]线程执行,任务class:[{}],请检查优化应用程序,corePoolSize：[{}],MaximumPoolSize:[{}],ActiveCount:[{}]",
                Thread.currentThread().getName(),
                ClassUtils.getSimpleName(r.getClass()),
                corePoolSize,
                maximumPoolSize,
                activeCount
        );
        super.rejectedExecution(r, e);
    }
}
