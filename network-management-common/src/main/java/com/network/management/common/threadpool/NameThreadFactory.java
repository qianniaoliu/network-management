package com.network.management.common.threadpool;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link NameThreadFactory} name of ThreadFactory {@link ThreadFactory}
 * @author yyc
 * @date 2020/9/19 09:30
 * @since 1.0.0
 */
public class NameThreadFactory implements ThreadFactory {
    /**
     * default name of ThreadFactory {@link ThreadFactory}
     */
    private String threadPoolPrefix = "ThreadPoolExecutors-Thread-";
    public AtomicInteger counter = new AtomicInteger(0);

    /**
     * set thread pool name for ThreadFactory
     * @param name thread pool name
     */
    public NameThreadFactory(String name){
        if(Objects.nonNull(name) && !name.isEmpty()){
            threadPoolPrefix = name;
        }
    }

    /**
     * default constructor of NameThreadFactory {@link NameThreadFactory}
     */
    public NameThreadFactory(){

    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(threadPoolPrefix + counter.incrementAndGet());
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
