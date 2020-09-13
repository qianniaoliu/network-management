package com.network.management.common.threadpool.handler;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {@link RequeueRejectedExecutionHandler} requeue ExecutorService of RejectedExecutionHandler {@link RejectedExecutionHandler}
 * @author yyc
 * @date 2020/5/13 11:46
 * @since 1.0.0
 */
public class RequeueRejectedExecutionHandler implements RejectedExecutionHandler {
    /**
     * time out
     */
    private static final int TIME_OUT = 60;
    @Override
    public void rejectedExecution(final Runnable r, final ThreadPoolExecutor executor) {
        try {
            executor.getQueue().offer(r, TIME_OUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RejectedExecutionException("Interrupted waiting for BrokerService.worker");
        }
        throw new RejectedExecutionException("Timed Out while attempting to enqueue Task.");
    }
}
