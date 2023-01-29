package cn.minsin.core.thread.override;

import cn.minsin.core.tools.log.GlobalDefaultLogger;

/**
 * @author minsin/mintonzhang@163.com
 * @since 2022/7/7
 */
@FunctionalInterface
public interface RunnablePlus extends Runnable {


    @Override
    default void run() {
        try {
            this.beforeRunInit();
            this.doRun();
            this.whenRunSuccess();
        } catch (Exception e) {
            this.whenRunHasError(e);
        } finally {
            this.doFinal();
        }
    }

    /**
     * 执行前
     */
    default void beforeRunInit() {

    }

    /**
     * 执行成功
     */
    default void whenRunSuccess() {

    }

    /**
     * 肯定会执行的逻辑
     */
    default void doFinal() {

    }

    /**
     * 真实的处理run逻辑
     *
     * @throws Exception 可能会抛出异常
     */
    void doRun() throws Exception;

    /**
     * 执行异常
     *
     * @param throwable 异常
     */
    default void whenRunHasError(Throwable throwable) {
        GlobalDefaultLogger.log.error("任务执行失败", throwable);
    }

}
