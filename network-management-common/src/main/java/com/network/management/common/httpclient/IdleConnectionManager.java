package com.network.management.common.httpclient;



import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * httpclient空闲连接管理类
 * @Author: yyc
 * @Date: 2020/9/12 23:18
 * @Version 1.0
 */
public class IdleConnectionManager extends Thread {
    private static final Logger logger = Logger.getLogger(IdleConnectionManager.class.getName());

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
            if (logger.isLoggable(Level.WARNING)) {
                logger.log(Level.WARNING, "IdleConnectionManager exception, cause: " + e.toString(), e);
            }
        }
    }

    /**
     * IdleConnectionManager shutDown
     */
    public void shutdown() {
        shutdown = true;
    }
}
