package cn.minsin.core.thread.pool;


import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author minton.zhang
 * @since 2020/12/22 下午2:33
 */
public class BusinessThreadPool {


    public static ThreadPoolExecutor getInstance() {
        return Holder.getExecutor();
    }


    public static void initThreadPool(int coreSize, int maxSize, int timeoutSeconds, int queueSize) {
        Holder.setExecutor(StandardThreadPoolHelper.create(coreSize, maxSize, timeoutSeconds, queueSize));
    }

    private static class Holder {

        private final static Object LOCKER = new Object();
        /**
         * 定长为1的线程池
         */
        private static ThreadPoolExecutor executor;

        public static ThreadPoolExecutor getExecutor() {
            if (executor == null) {
                setExecutor();
            }
            return executor;
        }

        public static void setExecutor(ThreadPoolExecutor taskExecutor) {

            synchronized (LOCKER) {
                if (taskExecutor != null && executor == null) {
                    executor = taskExecutor;
                }
            }
        }

        public static void setExecutor() {
            setExecutor(StandardThreadPoolHelper.createDefault());
        }
    }

}
