package com.network.management.common.httpclient;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * httpclient空闲连接管理类
 *
 * @Author: yyc
 * @Date: 2020/9/12 23:18
 * @Version 1.0
 */
@Slf4j
public class IdleConnectionManager extends Thread {
    private final PoolingHttpClientConnectionManager connMgr;
    private volatile boolean shutdown = false;

    /**
     * 空闲时间(s)
     */
    private final int IDLE_TIME = 10;
    /**
     * 线程sleep时间(ms)
     */
    private final int THREAD_SLEEP_TIME = 5000;

    public IdleConnectionManager(PoolingHttpClientConnectionManager connMgr) {
        super();
        this.connMgr = connMgr;
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                Thread.sleep(THREAD_SLEEP_TIME);
                connMgr.closeExpiredConnections();
                connMgr.closeIdleConnections(IDLE_TIME, TimeUnit.SECONDS);
            }
        } catch (Throwable e) {
            log.warn("IdleConnectionManager exception, cause: " + e.toString(), e);
        }
    }

    /**
     * IdleConnectionManager shutDown
     */
    public void shutdown() {
        shutdown = true;
    }
}
