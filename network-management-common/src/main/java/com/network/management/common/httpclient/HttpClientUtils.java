package com.network.management.common.httpclient;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * 支持高并发http/https请求的工具类
 *
 * @author: yyc
 * @date: 2020/9/12 23:18
 * @Version 1.0
 */
@Slf4j
public class HttpClientUtils {
    /**
     * 超时配置
     *
     * @return
     */
    private static RequestConfig config(int timeOut) {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(timeOut)
                .setConnectTimeout(timeOut)
                .setSocketTimeout(timeOut)
                .build();
    }

    /**
     * close streams
     *
     * @param httpResponse
     * @param httpEntity
     */
    private static void closeStreams(CloseableHttpResponse httpResponse, HttpEntity httpEntity) {
        if (null != httpEntity) {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
            }
        }
        if (null != httpResponse) {
            try {
                httpResponse.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * httpPost
     *
     * @param url
     * @param headers
     * @param body
     * @return
     * @throws HttpException
     */
    public static String doPost(String url, Map<String, String> headers, HttpEntity entity, int timeOut) throws HttpException {
        String response = null;
        CloseableHttpResponse httpResponse = null;
        HttpEntity httpEntity = null;
        try {
            HttpPost request = new HttpPost(url);
            request.setConfig(config(timeOut));
            if (null != headers && !headers.isEmpty()) {
                for (Map.Entry<String, String> e : headers.entrySet()) {
                    request.addHeader(e.getKey(), e.getValue());
                }
            }
            if (null != entity) {
                request.setEntity(entity);
            }
            httpResponse = HttpClientManager.getInstance().getCloseableHttpClient().execute(request);
            if (httpResponse != null &&
                    (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK
                            || httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY)) {
                httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
            }
        } catch (Exception e) {
            throw new HttpException("httpPost meet error, the url:" + url, e);
        } finally {
            closeStreams(httpResponse, httpEntity);
        }
        return response;
    }

    /**
     * httpclient post请求
     * @param url 请求url
     * @param entity {@link HttpEntity}
     * @param timeOut 超时时间
     * @return
     * @throws HttpException
     */
    public static String doPost(String url, HttpEntity entity, int timeOut) throws HttpException {
        return doPost(url, null, entity, timeOut);
    }

    /**
     * httpGet
     *
     * @param url
     * @param timeOut
     * @param encodeStr
     * @return
     * @throws HttpException
     */
    public static String doGet(String url, Map<String, String> headers, int timeOut, String encodeStr) throws HttpException {
        String response = null;
        CloseableHttpResponse httpResponse = null;
        HttpEntity httpEntity = null;
        try {
            HttpGet request = new HttpGet(url);
            request.setConfig(config(timeOut));
            if (null != headers && !headers.isEmpty()) {
                for (Map.Entry<String, String> e : headers.entrySet()) {
                    request.addHeader(e.getKey(), e.getValue());
                }
            }
            httpResponse = HttpClientManager.getInstance().getCloseableHttpClient().execute(request);
            if (Objects.nonNull(httpResponse) &&
                    httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity, StringUtils.isEmpty(encodeStr) ? HTTP.UTF_8 : encodeStr);
            }
        } catch (Exception e) {
            throw new HttpException("httpGet meet error, the url:" + url, e);
        } finally {
            closeStreams(httpResponse, httpEntity);
        }
        return response;
    }

    /**
     * get请求
     *
     * @param url
     * @param timeOut 超时时间
     * @return
     * @throws HttpException
     */
    public static String doGet(String url, int timeOut) throws HttpException {
        return doGet(url, null, timeOut, HTTP.UTF_8);
    }
}
