package cn.minsin.core.thread.pool;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public interface ScheduleThreadPoolHelper {

    static ThreadPoolTaskScheduler createDefault() {
        int coreSize = Runtime.getRuntime().availableProcessors();
        return create(coreSize, 30);
    }

    static ThreadPoolTaskScheduler create(int coreSize, int timeoutSeconds) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        // 配置池子的大小
        threadPoolTaskScheduler.setPoolSize(coreSize);
        // 设置名字
        threadPoolTaskScheduler.setThreadNamePrefix("spring-task-");
        // 设置等待任务在关机时完成
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        // 设置等待终止时间
        threadPoolTaskScheduler.setAwaitTerminationSeconds(timeoutSeconds);

        return threadPoolTaskScheduler;
    }

}
