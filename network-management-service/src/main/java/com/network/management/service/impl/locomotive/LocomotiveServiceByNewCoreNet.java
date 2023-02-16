package com.network.management.service.impl.locomotive;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.network.management.common.httpclient.HttpClientUtils;
import com.network.management.domain.dao.Locomotive;
import com.network.management.domain.enums.LocomotiveTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.protocol.HTTP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class LocomotiveServiceByNewCoreNet extends AbstractLocomotiveServiceImpl{

    /**
     * 新网管url
     */
    private static final String newCoreUrl = "http://%s/ueInfo.html?query=true&filter={\"userStateInfo.5gsUserState\":\"{\\\"$in\\\":[\\\"CONNECTED\\\",\\\"IDLE\\\"]}\",\"imsi\":\"{\\\"$regex\\\":\\\"^\\\"}\"}";
    /**
     * get请求参数
     * http://172.25.11.12/ueInfo.html?query=true&filter={"imsi":{"$regex":"^"},"userStateInfo.5gsUserState":{"$in":["CONNECTED"]}}
     */
    private static final String newCoreUrlParams = "?query=true&filter={%22imsi%22:{%22$regex%22:%22^%22},%22userStateInfo.5gsUserState%22:{%22$in%22:[%22CONNECTED%22,%22IDLE%22]}}";


    /**
     * cookie token
     */
    private static final String pub_key ="pub_key=-----BEGIN%20PUBLIC%20KEY-----%0AMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGu1fd%2Fkiub70mcQiV618B5tFt%0AoeOB9wtdW9tceOxo%2F1o4gRd3%2BUnqAVOGU4x38hKjQt121QazXrFhIDT%2BdedEnA3K%0A%2BBd%2BAHbwW%2B9b2kPx9C3RWZrCPBGgIwmBsVv0IfYYnxhG47jsUSwR78JvEJE0uqmj%0Aehx1s8vi9UbIpVzTuwIDAQAB%0A-----END%20PUBLIC%20KEY-----; login_userName=admin; nfId=mme01; nfName=mme01";


    private static final String csrfTokenKey = "csrftoken=%s; ";

    private static final String accessTokenKey = "access_token=%s; ";

    private static final String csrfToken2 = "csrftoken=%s; pub_key=-----BEGIN PUBLIC KEY-----\n" +
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGu1fd/kiub70mcQiV618B5tFt\n" +
            "oeOB9wtdW9tceOxo/1o4gRd3+UnqAVOGU4x38hKjQt121QazXrFhIDT+dedEnA3K\n" +
            "+Bd+AHbwW+9b2kPx9C3RWZrCPBGgIwmBsVv0IfYYnxhG47jsUSwR78JvEJE0uqmj\n" +
            "ehx1s8vi9UbIpVzTuwIDAQAB\n" +
            "-----END PUBLIC KEY-----; login_userName=admin; access_token=%s; nfId=mme01; nfName=mme01";

    /**
     * 机车链接状态
     */
    private static final Set<String> ConnectStatus = Sets.newHashSet("CONNECTED","IDLE");
    /**
     * 逗号
     */
    private static final String COMMA = ",";

     /**
     * 超时时间
     */
    @Value("${locomotive.time.out:5000}")
    private String timeOut;

    @Value("${new_core_csrftoken}")
    private String csrftoken;

    @Value("${new_core_access_token}")
    private String accessToken;

    @Override
    protected Map<String, String> queryLocomotiveLocations(String coreIp, List<Locomotive> locomotives) {
        if(StringUtils.isEmpty(coreIp) || CollectionUtils.isEmpty(locomotives)){
            log.warn("核心网ip为空或机车列表为空coreIp->{},locomotives->{}", coreIp, JSON.toJSONString(locomotives));
            return Maps.newHashMap();
        }
        try{
            String htmlContent = queryData(coreIp, getHeaderMap());
            Map<String, String> ueMap = parseHtmlContent(htmlContent);
            log.info("机车与核心网的映射关系为ueMap->{}", JSON.toJSONString(ueMap));
            return ueMap;
        }catch (Exception e){
            log.error("查询机车与核心网的关系失败coreIp->{},locomotives->{}", coreIp, JSON.toJSONString(locomotives), e);
        }
        return Maps.newHashMap();
    }


    /**
     * reload远端数据
     *
     * @param coreIp 核心网ip
     * @param headers headers
     */
    private String queryData(String coreIp, Map<String, String> headers) {
        try {
            String url = String.format(newCoreUrl, coreIp);
            return HttpClientUtils.doGet(url, headers, null, Integer.parseInt(timeOut), HTTP.UTF_8);
        } catch (Exception ex) {
            log.error("LocomotiveServiceByNewCoreNet.queryData coreIp->{},headers->{}", coreIp, JSON.toJSONString(headers), ex);
        }
        return null;
    }


    /**
     * 封装url后缀
     * @return
     */
    private Map<String, String> getParameters(){
        Map<String, String> params = Maps.newHashMap();
        params.put("query", JSON.toJSONString(Boolean.TRUE));

        Map<String, String> regexMap = Maps.newHashMap();
        regexMap.put("$regex", "^");

        Map<String, String> inMap = Maps.newHashMap();
        inMap.put("$in", JSON.toJSONString(Lists.newArrayList("CONNECTED", "IDLE")));

        Map<String, String> filterMap = Maps.newHashMap();
        filterMap.put("imsi", JSON.toJSONString(regexMap));
        filterMap.put("userStateInfo.5gsUserState", JSON.toJSONString(inMap));

        params.put("filter", JSON.toJSONString(filterMap));

        return params;
    }

    /**
     * 解析机车数据
     * @param htmlContent 机车数据
     * @return key:ueIp value:基站ip
     */
    private Map<String, String> parseHtmlContent(String htmlContent) {
        Map<String, String> result = new HashMap<>();
        if (StringUtils.isEmpty(htmlContent)) {
            log.warn("机车与核心网原始数据为空");
        }
        Document doc = Jsoup.parse(htmlContent);
        Elements rows = doc.select("table[class=table table-bordered table-hover]").get(0).select("tr");
        if (Objects.isNull(rows) || rows.size() <= 1){
            log.warn("imsi机车数据为空");
        }
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements tdElements = row.select("td");
            if(Objects.isNull(tdElements) || tdElements.size() < 5){
                log.warn("imsi机车数据格式有错误tdElements->{}", JSON.toJSONString(tdElements));
                continue;
            }
            String imsi = tdElements.get(0).text();
            String ueIp = tdElements.get(1).text();
            String status = tdElements.get(2).text();
            String eNodeBIP = tdElements.get(4).text();
            String newUeIp = getUeIp(ueIp);
            if(StringUtils.isNotEmpty(newUeIp) && StringUtils.isNotEmpty(eNodeBIP) && ConnectStatus.contains(status.trim())){
                result.put(newUeIp, eNodeBIP);
            }
        }
        return result;
    }

    /**
     * 获取ueIp
     * @param ueIp
     * @return
     */
    private String getUeIp(String ueIp){
        if(StringUtils.isEmpty(ueIp)){
            return null;
        }
        if(ueIp.contains(COMMA)){
            return ueIp.split(COMMA)[0];
        }
        return ueIp;
    }


    /**
     * 封装请求headers
     * @return
     */
    private Map<String, String> getHeaderMap() {
        Map<String, String> header = Maps.newHashMap();
        header.put("Content-Type", "application/x-www-form-urlencoded");
//        String cookie = String.format(csrfTokenKey, csrftoken) + String.format(accessTokenKey, accessToken) + pub_key;
        String cookie2 = String.format(csrfToken2, csrftoken, accessToken);
        header.put("Cookie", cookie2);
        return header;
    }

    @Override
    public boolean isSupport(String type) {
        return getType().equals(type);
    }

    @Override
    public String getType() {
        return LocomotiveTypeEnum.NEW_CORE_QUERY.getType();
    }
}
