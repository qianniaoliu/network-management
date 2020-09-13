package com.network.management.common.threadpool;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池工具类
 *
 * @author yyc
 * @date 2020/9/13 14:12
 */
public class ThreadPoolUtils {
    /**
     * 数据采集线程池
     */
    private static ExecutorService collectExecutor;
    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 20;
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 50;
    /**
     * 保存时间
     */
    private static final Long KEEP_TIME = 0L;

    private static final int QUEUE_NUMBER = 1000;

    private static final String THREAD_POOL_NAME = "collect-device-status";

    /**
     * 获取线程池
     * @return {@link ExecutorService}
     */
    public synchronized static ExecutorService getExecutorService() {
        if (Objects.isNull(collectExecutor)) {
            collectExecutor = ThreadPoolExecutors.getExecutorService(CORE_POOL_SIZE, MAX_POOL_SIZE,
                    KEEP_TIME, new LinkedBlockingQueue<Runnable>(QUEUE_NUMBER), THREAD_POOL_NAME,
                    new ThreadPoolExecutor.AbortPolicy());
        }
        return collectExecutor;
    }
}
