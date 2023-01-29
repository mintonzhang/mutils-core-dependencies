package cn.minsin.core.tools;

import cn.minsin.core.tools.log.GlobalDefaultLogger;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author minsin/mintonzhang@163.com
 * @since 2022/5/12
 */
public class ThreadUtil implements GlobalDefaultLogger {

    @Deprecated
    public static void await(CountDownLatch count, int minutes) {
        try {
            boolean await = count.await(minutes, TimeUnit.MINUTES);
            if (!await) {
                log.error("countdownLatch未在规定时间结束");
            }
            //return await;
        } catch (InterruptedException e) {
            log.error("countdownLatch被打断", e);
        }
        //return false;


    }

    public static void await(CountDownLatch count, String countDownLatchName, int minutes) {
        try {
            boolean await = count.await(minutes, TimeUnit.MINUTES);
            if (!await) {
                log.error("CountDownLatch:[{}],未在规定的[{}]分钟内结束", countDownLatchName, minutes);
            }
            //return await;
        } catch (InterruptedException e) {
            log.error("CountDownLatch:[{}],出现异常", countDownLatchName, e);
        }
        //return false;


    }

    @Deprecated
    public static void await(CyclicBarrier count, int minutes) {
        try {
            count.await(minutes, TimeUnit.MINUTES);
        } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
            log.error("CyclicBarrier被打断", e);
        }

    }


    public static void await(CyclicBarrier count, String cyclicBarrierName, int minutes) {
        try {
            count.await(minutes, TimeUnit.MINUTES);
        } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
            log.error("CyclicBarrier:[{}],出现异常", cyclicBarrierName, e);
        }

    }

    //
    //public static void countDownAndAwait(int minutes, Collection<Runnable> runnable) {
    //    if (runnable.isEmpty()) {
    //        return;
    //    }
    //
    //    CountDownLatch countDownLatch = new CountDownLatch(runnable.size());
    //
    //    for (Runnable run : runnable) {
    //        SessionRunnableHelper override = new SessionRunnableHelper(run) {
    //            @Override
    //            public void doFinal() {
    //                countDownLatch.countDown();
    //            }
    //        };
    //        override.execute(BusinessThreadPool.getInstance());
    //    }
    //    ThreadUtil.await(countDownLatch, minutes);
    //
    //}
    //
    ///**
    // * @param minutes         每个任务最长阻塞时长
    // * @param runnable        子线程
    // * @param whenAllFinished 当所有任务都完成时
    // */
    //public static void cyclicBarrierAndAwait(int minutes, Collection<Runnable> runnable, Runnable whenAllFinished) {
    //    if (runnable.isEmpty()) {
    //        return;
    //    }
    //    CyclicBarrier cyclicBarrier = new CyclicBarrier(runnable.size(), whenAllFinished);
    //
    //    for (Runnable run : runnable) {
    //        SessionRunnableHelper override = new SessionRunnableHelper(run) {
    //            @Override
    //            public void doFinal() {
    //                ThreadUtil.await(cyclicBarrier, minutes);
    //            }
    //        };
    //        //不能使用线程池 不然会导致永远无法执行结束
    //        override.execute();
    //    }
    //
    //}

    @Deprecated
    public static void sleep(TimeUnit timeUnit, int duration) {
        try {
            timeUnit.sleep(duration);
        } catch (InterruptedException e) {
            log.error("Thread#sleep被打断", e);
        }

    }

    public static void sleep(TimeUnit timeUnit, int duration, String sleepName) {
        try {
            timeUnit.sleep(duration);
        } catch (InterruptedException e) {
            log.error("[{}]的线程休眠被异常打断", sleepName, e);
        }


    }
}
