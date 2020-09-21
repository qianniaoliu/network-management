package com.network.management.common.httpclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * httpClient管理类
 *
 * @author: yyc
 * @date: 2020/9/12 23:19
 * @Version 1.0
 */
@Slf4j
public class HttpClientManager {
    private static final Logger logger = Logger.getLogger(HttpClientManager.class.getName());
    /**
     * CloseableHttpClient instance
     */
    private CloseableHttpClient closeableHttpClient = null;
    private PoolingHttpClientConnectionManager connectionManager = null;
    private static volatile HttpClientManager instance;
    private SSLContext sslContext = null;
    /**
     * httpclient默认超时时间5s
     */
    private final int DEFAULT_TIMEOUT = 10000;
    /**
     * 最大并发http连接
     */
    private final int MAX_HTTP_CONNECTION = 200;
    /**
     * 最大路由数量
     */
    private final int MAX_PER_ROUTER = 20;
    private final String HTTP_PROTOCOL_PREFIX = "http";
    private final String HTTPS_PROTOCOL_PREFIX = "https";
    /**
     * 重试次数
     */
    private final int RETRY_NUMBER = 2;

    /**
     * init HttpClientManager
     */
    private HttpClientManager() {
        getCloseableHttpClient();
        if (null != connectionManager) {
            new IdleConnectionManager(connectionManager).start();
        }
    }

    /**
     * HttpClientManager instance
     *
     * @return {@link HttpClientManager}
     */
    public static HttpClientManager getInstance() {
        if (instance == null) {
            synchronized (HttpClientManager.class) {
                if (instance == null) {
                    instance = new HttpClientManager();
                }
            }
        }
        return instance;
    }


    /**
     * 在调用SSL之前需要重写验证方法，取消检测SSL
     * 创建ConnectionManager，添加Connection配置信息
     *
     * @return HttpClient 支持https
     */
    public CloseableHttpClient getCloseableHttpClient() {
        if (null == closeableHttpClient) {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                    .setExpectContinueEnabled(Boolean.TRUE)
                    .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                    .setConnectionRequestTimeout(DEFAULT_TIMEOUT)
                    .setConnectTimeout(DEFAULT_TIMEOUT)
                    .setSocketTimeout(DEFAULT_TIMEOUT)
                    .build();
            closeableHttpClient = HttpClients.custom()
                    .setSSLContext(getSslContext())
                    .setConnectionManager(getPoolingHttpClientConnectionManager())
                    .setRetryHandler(new MyHttpRequestRetryHandler())
                    .setDefaultRequestConfig(requestConfig)
                    .build();
        }
        return closeableHttpClient;
    }

    /**
     * init SSLContext
     *
     * @return {@link SSLContext}
     */
    private SSLContext getSslContext() {
        if (null == sslContext) {
            try {
                sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                    @Override
                    public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                        return true;
                    }
                }).build();
            } catch (Exception e) {
                if (logger.isLoggable(Level.INFO)) {
                    logger.log(Level.WARNING, "build SSLContext exception! cause: " + e.toString(), e);
                }
            }
        }
        return sslContext;
    }

    /**
     * get PoolingHttpClientConnectionManager
     *
     * @return
     */
    private PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {
        if (null == connectionManager) {
            try {
                SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(getSslContext(), NoopHostnameVerifier.INSTANCE);
                Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register(HTTP_PROTOCOL_PREFIX, PlainConnectionSocketFactory.INSTANCE)
                        .register(HTTPS_PROTOCOL_PREFIX, sslSocketFactory)
                        .build();
                connectionManager = new PoolingHttpClientConnectionManager(
                        socketFactoryRegistry);
                connectionManager.setMaxTotal(MAX_HTTP_CONNECTION);
                connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTER);
            } catch (Exception e) {
                if (logger.isLoggable(Level.WARNING)) {
                    logger.log(Level.WARNING, "Create PoolingHttpClientConnectionManager Exception, cause: " + e.toString(), e);
                }
            }
        }
        return connectionManager;
    }

    /**
     * MyHttpRequestRetryHandler
     */
    private class MyHttpRequestRetryHandler implements HttpRequestRetryHandler {
        @Override
        public boolean retryRequest(IOException exception,
                                    int executionCount, HttpContext context) {
            /**
             * 如果已经重试了2次，就放弃
             */
            if (executionCount >= RETRY_NUMBER) {
                return false;
            }
            /**
             * 如果服务器丢掉了连接，那么就重试
             */
            if (exception instanceof NoHttpResponseException) {
                return true;
            }
            /**
             * 不要重试SSL握手异常
             */
            if (exception instanceof SSLHandshakeException) {
                return false;
            }
            /**
             * 连接被拒绝、超时则继续重试
             */
            if (exception instanceof ConnectTimeoutException ||
                    exception instanceof HttpHostConnectException ||
                    exception instanceof SocketTimeoutException) {
                logger.info("retryRequest() 连接超时、异常继续重试...");
                return true;
            }
            /**
             * 超时
             */
            if (exception instanceof InterruptedIOException) {
                return false;
            }
            /**
             * 目标服务器不可达
             */
            if (exception instanceof UnknownHostException) {
                return false;
            }
            /**
             * SSL握手异常
             */
            if (exception instanceof SSLException) {
                return false;
            }

            HttpClientContext clientContext = HttpClientContext
                    .adapt(context);
            HttpRequest request = clientContext.getRequest();
            /**
             * 如果请求是幂等的，就再次尝试
             */
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        }
    }
}
