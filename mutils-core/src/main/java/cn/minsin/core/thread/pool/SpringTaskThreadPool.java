package cn.minsin.core.thread.pool;


import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author minton.zhang
 * @since 2020/12/22 下午2:33
 */
public class SpringTaskThreadPool {


    public static ThreadPoolTaskScheduler getInstance() {
        return Holder.getExecutor();
    }


    public static void initThreadPool(int coreSize, int timeoutSeconds) {
        Holder.setExecutor(ScheduleThreadPoolHelper.create(coreSize, timeoutSeconds));
    }

    private static class Holder {

        private final static Object LOCKER = new Object();
        /**
         * 定长为1的线程池
         */
        private static ThreadPoolTaskScheduler executor;

        public static ThreadPoolTaskScheduler getExecutor() {
            if (executor == null) {
                setExecutor();
            }
            return executor;
        }

        public static void setExecutor(ThreadPoolTaskScheduler taskExecutor) {

            synchronized (LOCKER) {
                if (taskExecutor != null && executor == null) {
                    executor = taskExecutor;
                }
            }
        }

        public static void setExecutor() {
            setExecutor(ScheduleThreadPoolHelper.createDefault());
        }
    }
}
