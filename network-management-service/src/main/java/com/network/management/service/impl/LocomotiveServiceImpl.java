package com.network.management.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.network.management.common.httpclient.HttpClientUtils;
import com.network.management.common.threadpool.ThreadPoolUtils;
import com.network.management.domain.dao.BordInformation;
import com.network.management.domain.dao.Locomotive;
import com.network.management.domain.enums.LocomotiveConnectEnum;
import com.network.management.domain.enums.YnEnum;
import com.network.management.domain.search.LocomotiveSearch;
import com.network.management.domain.search.Page;
import com.network.management.domain.vo.LocomotiveVo;
import com.network.management.mapper.BordInformationMapper;
import com.network.management.mapper.LocomotiveMapper;
import com.network.management.service.LocomotiveService;
import com.network.management.service.converter.LocomotiveConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * 机车服务实现类
 *
 * @author yyc
 * @date 2020/10/14 22:50
 */
@Service
@Slf4j
public class LocomotiveServiceImpl implements LocomotiveService {
    @Autowired
    private LocomotiveMapper locomotiveMapper;
    @Autowired
    private LocomotiveConverter locomotiveConverter;
    @Autowired
    private BordInformationMapper bordInformationMapper;
    /**
     * 前一次机车及状态信息
     */
    private static Map<String, LocomotiveVo>  preLocomotiveVoMap = new HashMap<>();
    /**
     * 核心网获取基站与机车ip对应关系的url
     */
    private static final String CORE_NETWORK_URL = "http://%s/UEInfo.html?ueIp=%s&msisdn=&enodebIp=&registed=100&online=20";

    private static final String TABLE_CSS_QUERY = "table[class=table table-bordered]";
    private static final String TR_CSS_QUERY = "tr";
    private static final String TD_CSS_QUERY = "td";
    /**
     * 默认ip
     */
    private static final String DEFAULT_IP = "0.0.0.0";
    /**
     * 超时时间
     */
    @Value("${locomotive.time.out:5000}")
    private String timeOut;
    /**
     * token
     */
    private static final String COOKIE_KEY = "Cookie";
    private static final String COOKIE_CSRF_TOKEN = "csrftoken";
    private static final String COOKIE_CSRF_TOKEN_VALUE = "9qNwGnKW05FxWWGSH9nTLGMHNfdVNbEQ";
    private static final String COOKIE_LOGIN_USERNAME = "login_userName";
    private static final String COOKIE_LOGIN_USERNAME_VALUE = "admin";
    private static final String COOKIE_LOGIN_USER_TYPE = "login_userType";
    private static final String COOKIE_LOGIN_USER_TYPE_VALUE = "Administrator";
    private static final String COOKIE_DEVICE_TYPE = "deviceType";
    private static final String COOKIE_DEVICE_TYPE_VALUE = "MINI_EPC";

    @Override
    public Map<String, List<LocomotiveVo>> queryLocomotiveStatus() {
        Map<String, List<LocomotiveVo>> locomotiveMap = new HashMap<>();
        List<BordInformation> bordInformations = bordInformationMapper.selectAll();
        if (CollectionUtils.isNotEmpty(bordInformations)) {
            BordInformation bordInformation = bordInformations.get(0);
            if (Objects.nonNull(bordInformation) && StringUtils.isNotEmpty(bordInformation.getCoreIp())) {
                List<Locomotive> locomotives = locomotiveMapper.queryAllLocomotives();
                List<LocomotiveVo> locomotiveVos = getLocomotiveVos(bordInformation.getCoreIp(), locomotives);
                List<LocomotiveVo> allLocomotiveVos = getAllLocomotiveVos(locomotiveVos);
                ListUtils.emptyIfNull(allLocomotiveVos)
                        .stream()
                        .filter(Objects::nonNull)
                        .forEach(locomotiveVo -> {
                            if (StringUtils.isNotEmpty(locomotiveVo.getENodeBIP())) {
                                if (locomotiveMap.containsKey(locomotiveVo.getENodeBIP())) {
                                    locomotiveMap.get(locomotiveVo.getENodeBIP()).add(locomotiveVo);
                                } else {
                                    locomotiveMap.put(locomotiveVo.getENodeBIP(), Lists.newArrayList(locomotiveVo));
                                }
                            }
                        });

            }
        }
        return locomotiveMap;
    }

    /**
     * 对比前一次机车及状态，填充前一次的机车信息
     * @param locomotiveVos {@link List<LocomotiveVo>}
     * @return {@link List<LocomotiveVo>}
     */
    private List<LocomotiveVo> getAllLocomotiveVos(List<LocomotiveVo> locomotiveVos){
        List<LocomotiveVo> newLocomotiveVos = new ArrayList<>();
        Map<String, LocomotiveVo> newLocomotiveVoMap = new HashMap<>(preLocomotiveVoMap);
        ListUtils.emptyIfNull(locomotiveVos)
                .stream()
                .filter(Objects::nonNull)
                .forEach(locomotiveVo -> {
                    preLocomotiveVoMap.put(locomotiveVo.getUeIp(), locomotiveVo);
                    newLocomotiveVos.add(locomotiveVo);
                    newLocomotiveVoMap.remove(locomotiveVo.getUeIp());
                });
        if(MapUtils.isNotEmpty(newLocomotiveVoMap)){
            for(Map.Entry<String, LocomotiveVo> entry : newLocomotiveVoMap.entrySet()){
                if(Objects.nonNull(entry) && StringUtils.isNotEmpty(entry.getKey())
                        && Objects.nonNull(entry.getValue())){
                    LocomotiveVo locomotiveVo = entry.getValue();
                    locomotiveVo.setStatus(YnEnum.NO.getCode());
                    newLocomotiveVos.add(locomotiveVo);
                    preLocomotiveVoMap.put(entry.getKey(), locomotiveVo);
                }
            }
        }
        return newLocomotiveVos;
    }

    @Override
    public Integer saveLocomotive(LocomotiveVo locomotiveVo) {
        Locomotive locomotive = locomotiveConverter.convert(locomotiveVo);
        if (Objects.nonNull(locomotive)) {
            return locomotiveMapper.insert(locomotive);
        } else {
            log.error("新增机车失败:{}", JSON.toJSONString(locomotiveVo));
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        locomotiveMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateLocomotive(LocomotiveVo locomotiveVo) {
        Locomotive locomotive = locomotiveConverter.convert(locomotiveVo);
        if (Objects.nonNull(locomotive)) {
            locomotiveMapper.updateByPrimaryKey(locomotive);
        } else {
            log.error("修改机车失败:{}", JSON.toJSONString(locomotiveVo));
        }
    }

    @Override
    public List<LocomotiveVo> queryAllLocomotiveVos() {
        return locomotiveConverter.reverseConvertToList(locomotiveMapper.queryAllLocomotives());
    }

    @Override
    public Page<LocomotiveVo> search(LocomotiveSearch search) {
        Page<LocomotiveVo> locomotiveVoPage = new Page<LocomotiveVo>();
        if(Objects.isNull(search)){
            search = new LocomotiveSearch();
        }
        if(Objects.isNull(search.getCurrentPage())){
            search.setCurrentPage(1);
        }
        if(Objects.isNull(search.getPageSize())){
            search.setPageSize(10);
        }
        locomotiveVoPage.setCurrentPage(search.getCurrentPage());
        locomotiveVoPage.setPageSize(search.getPageSize());
        Integer count = locomotiveMapper.count(search);
        locomotiveVoPage.setCount(count);
        if(Objects.nonNull(count) && count > 0){
            locomotiveVoPage.setData(locomotiveConverter.reverseConvertToList(locomotiveMapper.getByConditions(search)));
        }else {
            locomotiveVoPage.setData(Lists.newArrayList());
        }
        return locomotiveVoPage;
    }

    /**
     * 获取机车数据及状态
     *
     * @param coreIp      核心网ip
     * @param locomotives {@link List<Locomotive>}
     * @return {@link List<LocomotiveVo>}
     */
    private List<LocomotiveVo> getLocomotiveVos(String coreIp, List<Locomotive> locomotives) {
        List<LocomotiveVo> locomotiveVos = new ArrayList<>();
        if (StringUtils.isNotEmpty(coreIp) && CollectionUtils.isNotEmpty(locomotives)) {
            try {
                CountDownLatch countDownLatch = new CountDownLatch(locomotives.size());
                locomotives.stream()
                        .forEach(locomotive -> {
                            ThreadPoolUtils.getLocomotiveExecutor().execute(() -> {
                                try {
                                    LocomotiveVo locomotiveVo = queryLocomotiveStatus(coreIp, locomotive);
                                    if (Objects.nonNull(locomotiveVo)) {
                                        locomotiveVos.add(locomotiveVo);
                                    }
                                } catch (Exception e) {
                                    log.error("获取机车数据失败:{}", JSON.toJSONString(locomotive), e);
                                } finally {
                                    countDownLatch.countDown();
                                }
                            });
                        });
                countDownLatch.await();
            } catch (Exception e) {
                log.error("获取所有机车状态错误", e);
            }
        }
        return locomotiveVos;
    }

    /**
     * 获取机车数据以及机车状态
     *
     * @param coreNetIp
     * @param locomotive
     * @return
     */
    private LocomotiveVo queryLocomotiveStatus(String coreNetIp, Locomotive locomotive) {
        try {
            String result = HttpClientUtils.doGet(String.format(CORE_NETWORK_URL, coreNetIp, locomotive.getUeIp()), Integer.parseInt(timeOut));
            return parseHtmlContent(result, locomotive);
        } catch (Exception e) {
            log.error("机车请求ip数据失败", e);
        }
        return null;
    }

    /**
     * 根据核心网数据获取机车数据
     *
     * @param htmlContent
     * @param locomotive  {@link Locomotive}
     * @return {@link LocomotiveVo}
     */
    private LocomotiveVo parseHtmlContent(String htmlContent, Locomotive locomotive) {
        if (StringUtils.isNotEmpty(htmlContent)) {
            Document doc = Jsoup.parse(htmlContent);
            Elements rows = doc.select(TABLE_CSS_QUERY).get(0).select(TR_CSS_QUERY);
            if (Objects.nonNull(rows) && rows.size() > 1) {
                for (int i = 1; i < rows.size(); i++) {
                    Element row = rows.get(i);
                    String ueIp = row.select(TD_CSS_QUERY).get(0).text();
                    String status = row.select(TD_CSS_QUERY).get(1).text();
                    String eNodeBIP = row.select(TD_CSS_QUERY).get(3).text();
                    if (isValid(ueIp, status, eNodeBIP)) {
                        boolean isReachable = isReachable(ueIp);
                        LocomotiveVo locomotiveVo = locomotiveConverter.reverseConvert(locomotive);
                        if (Objects.nonNull(locomotiveVo)) {
                            locomotiveVo.setStatus(isReachable ? YnEnum.YES.getCode() : YnEnum.NO.getCode());
                            locomotiveVo.setENodeBIP(eNodeBIP);
                        }
                        return locomotiveVo;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 是否有效的行
     *
     * @param ueIp
     * @param status
     * @param eNodeBIP
     * @return
     */
    private boolean isValid(String ueIp, String status, String eNodeBIP) {
        return StringUtils.isNotEmpty(ueIp) && StringUtils.isNotEmpty(eNodeBIP)
                && LocomotiveConnectEnum.isConnect(status)
                && !DEFAULT_IP.equals(eNodeBIP);
    }

    /**
     * ping机车状态
     *
     * @param ip
     * @return
     */
    private boolean isReachable(String ip) {
        boolean status = false;
        try {
            status = InetAddress.getByName(ip).isReachable(Integer.parseInt(timeOut));
        } catch (IOException e) {
            log.error(String.format("机车ip:%s 网络连通失败.", ip), e);
        }
        return status;
    }
}
