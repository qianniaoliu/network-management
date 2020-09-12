package com.network.management.common.threadpool;

import com.network.management.common.threadpool.handler.RejectedExecutionHandlerChain;
import com.network.management.common.threadpool.handler.RequeueRejectedExecutionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * {@link OverseaThreadPoolExecutor} wrapper of ThreadPoolExecutor {@link ThreadPoolExecutor}
 *
 * @author yyc
 * @date 2020/5/12 17:59
 * @since 1.0.0
 */
public class OverseaThreadPoolExecutor {
    /**
     * default queue number
     */
    private static final int DEFAULT_QUEUE_NUMBER = 2000;

    private OverseaThreadPoolExecutor() {

    }

    /**
     * wrapper of ThreadPoolExecutor {@link ThreadPoolExecutor},recommended to use thread factory name
     *
     * @param poolSize corePoolSize == maximumPoolSize
     * @return {@link ExecutorService}
     */
    @Deprecated
    public static ExecutorService overseaExecutorService(Integer poolSize) {
        return overseaExecutorService(poolSize, poolSize);
    }

    /**
     * wrapper of ThreadPoolExecutor {@link ThreadPoolExecutor},recommended to use thread factory name
     *
     * @param corePoolSize core pool size
     * @param maxPoolSize  max pool size
     * @return {@link ExecutorService}
     */
    @Deprecated
    public static ExecutorService overseaExecutorService(Integer corePoolSize, Integer maxPoolSize) {
        return overseaExecutorService(corePoolSize, maxPoolSize, DEFAULT_QUEUE_NUMBER);
    }

    /**
     * wrapper of ThreadPoolExecutor {@link ThreadPoolExecutor},recommended to use thread factory name
     *
     * @param corePoolSize core pool size
     * @param maxPoolSize  max pool size
     * @param queueNum     queue number
     * @return {@link ExecutorService}
     */
    @Deprecated
    public static ExecutorService overseaExecutorService(Integer corePoolSize, Integer maxPoolSize, Integer queueNum) {
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(queueNum),
                new NameThreadFactory());
    }

    /**
     * wrapper of ThreadPoolExecutor {@link ThreadPoolExecutor},with default queue number 2000.
     *
     * @param corePoolSize core pool size
     * @param poolName     thread factory name
     * @return {@link ExecutorService}
     */
    public static ExecutorService overseaExecutorService(Integer corePoolSize, String poolName) {
        return overseaExecutorService(corePoolSize, corePoolSize, poolName);
    }

    /**
     * wrapper of ThreadPoolExecutor {@link ThreadPoolExecutor},with default queue number 2000.
     *
     * @param corePoolSize core pool size
     * @param poolName     thread factory name
     * @param handler      implementation class of {@link RejectedExecutionHandler}
     * @return {@link ExecutorService}
     * @see ThreadPoolExecutor.CallerRunsPolicy
     * @see ThreadPoolExecutor.AbortPolicy
     * @see ThreadPoolExecutor.DiscardPolicy
     * @see ThreadPoolExecutor.DiscardOldestPolicy
     * @see RequeueRejectedExecutionHandler
     * @see RejectedExecutionHandlerChain
     */
    public static ExecutorService overseaExecutorService(Integer corePoolSize, String poolName, RejectedExecutionHandler handler) {
        return overseaExecutorService(corePoolSize, corePoolSize, poolName, handler);
    }

    /**
     * wrapper of ThreadPoolExecutor {@link ThreadPoolExecutor},with default queue number 2000.
     *
     * @param corePoolSize core pool size
     * @param maxPoolSize  max pool size
     * @param poolName     thread factory name
     * @return {@link ExecutorService}
     */
    public static ExecutorService overseaExecutorService(Integer corePoolSize, Integer maxPoolSize, String poolName) {
        return overseaExecutorService(corePoolSize, maxPoolSize, DEFAULT_QUEUE_NUMBER, poolName);
    }

    /**
     * wrapper of ThreadPoolExecutor {@link ThreadPoolExecutor}
     *
     * @param corePoolSize core pool size
     * @param maxPoolSize  max pool size
     * @param poolName     thread factory name
     * @param handler      implementation class of {@link RejectedExecutionHandler}
     * @return {@link ExecutorService}
     * @see ThreadPoolExecutor.CallerRunsPolicy
     * @see ThreadPoolExecutor.AbortPolicy
     * @see ThreadPoolExecutor.DiscardPolicy
     * @see ThreadPoolExecutor.DiscardOldestPolicy
     * @see RequeueRejectedExecutionHandler
     * @see RejectedExecutionHandlerChain
     */
    public static ExecutorService overseaExecutorService(Integer corePoolSize, Integer maxPoolSize, String poolName, RejectedExecutionHandler handler) {
        List<RejectedExecutionHandler> handles = new ArrayList<>();
        handles.add(handler);
        return overseaExecutorService(corePoolSize, maxPoolSize, DEFAULT_QUEUE_NUMBER, poolName, handles);
    }

    /**
     * wrapper of ThreadPoolExecutor {@link ThreadPoolExecutor}
     *
     * @param corePoolSize core pool size
     * @param maxPoolSize  max pool size
     * @param queueNum     queue number
     * @param poolName     thread factory name
     * @return {@link ExecutorService}
     */

    public static ExecutorService overseaExecutorService(Integer corePoolSize, Integer maxPoolSize, Integer queueNum, String poolName) {
        return overseaExecutorService(corePoolSize, maxPoolSize, queueNum, poolName, null);
    }

    /**
     * wrapper of ThreadPoolExecutor {@link ThreadPoolExecutor}
     *
     * @param corePoolSize core pool size
     * @param maxPoolSize  max pool size
     * @param queueNum     queue number
     * @param poolName     thread factory name
     * @param handlers     implementation classes of {@link RejectedExecutionHandler}
     * @return {@link ExecutorService}
     * @see ThreadPoolExecutor.CallerRunsPolicy
     * @see ThreadPoolExecutor.AbortPolicy
     * @see ThreadPoolExecutor.DiscardPolicy
     * @see ThreadPoolExecutor.DiscardOldestPolicy
     * @see RequeueRejectedExecutionHandler
     * @see RejectedExecutionHandlerChain
     */
    public static ExecutorService overseaExecutorService(Integer corePoolSize, Integer maxPoolSize, Integer queueNum, String poolName, List<RejectedExecutionHandler> handlers) {
        if (Objects.isNull(handlers) || handlers.size() == 0) {
            return new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(queueNum),
                    new NameThreadFactory(poolName));
        } else {
            return new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(queueNum),
                    new NameThreadFactory(poolName), RejectedExecutionHandlerChain.build(handlers));
        }
    }

    /**
     * wrapper of ThreadPoolExecutor {@link ThreadPoolExecutor}
     *
     * @param corePoolSize core pool size
     * @param maxPoolSize  max pool size
     * @param keepTime
     * @param queue
     * @param poolName     thread factory name
     * @param handler      implementation classes of {@link RejectedExecutionHandler}
     * @return {@link ExecutorService}
     * @see ThreadPoolExecutor.CallerRunsPolicy
     * @see ThreadPoolExecutor.AbortPolicy
     * @see ThreadPoolExecutor.DiscardPolicy
     * @see ThreadPoolExecutor.DiscardOldestPolicy
     * @see RequeueRejectedExecutionHandler
     * @see RejectedExecutionHandlerChain
     */
    public static ExecutorService overseaExecutorService(Integer corePoolSize, Integer maxPoolSize, long keepTime, BlockingQueue<Runnable> queue, String poolName, RejectedExecutionHandler handler) {
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepTime, TimeUnit.MILLISECONDS, queue,
                new NameThreadFactory(poolName), handler);
    }
}
