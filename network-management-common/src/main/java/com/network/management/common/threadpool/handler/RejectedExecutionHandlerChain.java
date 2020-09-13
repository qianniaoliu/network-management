package com.network.management.common.threadpool.handler;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * {@link RejectedExecutionHandlerChain} chain of RejectedExecutionHandler {@link RejectedExecutionHandler}
 * @author yyc
 * @date 2020/5/13 10:50
 * @since 1.0.0
 */
public class RejectedExecutionHandlerChain implements RejectedExecutionHandler {
    /**
     * RejectedExecutionHandler chain
     */
    private final RejectedExecutionHandler[] handlerChain;

    private RejectedExecutionHandlerChain(RejectedExecutionHandler[] handlerChain) {
        this.handlerChain = Objects.requireNonNull(handlerChain, "handlerChain can not be null");
    }

    public static RejectedExecutionHandler build(List<RejectedExecutionHandler> chain) {
        Objects.requireNonNull(chain, "handlerChain can not be null");
        RejectedExecutionHandler[] handlerChain = chain.toArray(new RejectedExecutionHandler[0]);
        return new RejectedExecutionHandlerChain(handlerChain);
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        for (RejectedExecutionHandler rejectedExecutionHandler : handlerChain) {
            rejectedExecutionHandler.rejectedExecution(r, executor);
        }
    }
}
