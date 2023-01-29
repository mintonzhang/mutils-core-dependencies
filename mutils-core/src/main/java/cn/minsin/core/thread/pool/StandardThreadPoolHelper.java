package cn.minsin.core.thread.pool;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public interface StandardThreadPoolHelper {

    static ThreadPoolExecutor createDefault() {
        int coreSize = Runtime.getRuntime().availableProcessors();
        return create(coreSize / 4, coreSize, 30, coreSize * 20);
    }

    static ThreadPoolExecutor create(int coreSize, int maxSize, int timeoutSeconds, int queueSize) {
        //使用netty的线程工厂
        DefaultThreadFactory namedThreadFactory = new DefaultThreadFactory("universal-runner");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                coreSize,
                maxSize,
                timeoutSeconds,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueSize)
        );
        threadPoolExecutor.setThreadFactory(namedThreadFactory);
        threadPoolExecutor.setRejectedExecutionHandler(new CallerRunsThenPrintPolicy());
        return threadPoolExecutor;
    }

}
