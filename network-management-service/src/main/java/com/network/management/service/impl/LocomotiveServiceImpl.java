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
import java.util.stream.Collectors;

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
    private static Map<String, LocomotiveVo> preLocomotiveVoMap = new HashMap<>();
    /**
     * 核心网获取基站与机车ip对应关系的url
     */
    private static final String CORE_NETWORK_URL = "http://%s/UEInfo.html?page=%s&msisdn=&enodebIp=&registed=11&online=10";
    private static final String RELOAD_URL = "http://%s/UEInfo.html?action=load";

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
                String firstHtmlContent = reloadRemoteData(bordInformation.getCoreIp());
                Map<String, String> ueNodeMapping = parseHtmlContent(firstHtmlContent);
                Integer pageCount = getPageCountFromHtmlContent(firstHtmlContent);
                ueNodeMapping.putAll(queryLocomotiveMap(bordInformation.getCoreIp(), pageCount));
                List<Locomotive> locomotives = locomotiveMapper.queryAllLocomotives();
                List<LocomotiveVo> locomotiveVos = convertLocomotiveVo(locomotives, ueNodeMapping);
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

    private List<LocomotiveVo> convertLocomotiveVo(List<Locomotive> locomotives, Map<String, String> ueNodeMapping) {
        List<LocomotiveVo> locomotiveVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(locomotives)){
            for(Locomotive locomotive : locomotives){
                if(ueNodeMapping.containsKey(locomotive.getUeIp())){
                    boolean isReachable = isReachable(locomotive.getUeIp());
                    LocomotiveVo locomotiveVo = locomotiveConverter.reverseConvert(locomotive);
                    String ueNodeIp = ueNodeMapping.get(locomotive.getUeIp());
                    if (Objects.nonNull(locomotiveVo) && StringUtils.isNotBlank(ueNodeIp)) {
                        locomotiveVo.setStatus(isReachable ? YnEnum.YES.getCode() : YnEnum.NO.getCode());
                        locomotiveVo.setENodeBIP(ueNodeIp);
                        locomotiveVos.add(locomotiveVo);
                    }
                }
            }
        }
        return locomotiveVos;
    }

    /**
     * reload远端数据
     *
     * @param coreIp 核心网ip
     */
    private String reloadRemoteData(String coreIp) {
        try {
            String url = String.format(RELOAD_URL, coreIp);
            return HttpClientUtils.doGet(url, Integer.parseInt(timeOut));
        } catch (Exception ex) {
            log.error("reload error!", ex);
        }
        return null;
    }

    /**
     * 对比前一次机车及状态，填充前一次的机车信息
     *
     * @param locomotiveVos {@link List<LocomotiveVo>}
     * @return {@link List<LocomotiveVo>}
     */
    private List<LocomotiveVo> getAllLocomotiveVos(List<LocomotiveVo> locomotiveVos) {
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
        if (MapUtils.isNotEmpty(newLocomotiveVoMap)) {
            for (Map.Entry<String, LocomotiveVo> entry : newLocomotiveVoMap.entrySet()) {
                if (Objects.nonNull(entry) && StringUtils.isNotEmpty(entry.getKey())
                        && Objects.nonNull(entry.getValue())) {
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
        if (Objects.isNull(search)) {
            search = new LocomotiveSearch();
        }
        if (Objects.isNull(search.getCurrentPage())) {
            search.setCurrentPage(1);
        }
        if (Objects.isNull(search.getPageSize())) {
            search.setPageSize(10);
        }
        locomotiveVoPage.setCurrentPage(search.getCurrentPage());
        locomotiveVoPage.setPageSize(search.getPageSize());
        Integer count = locomotiveMapper.count(search);
        locomotiveVoPage.setCount(count);
        if (Objects.nonNull(count) && count > 0) {
            locomotiveVoPage.setData(locomotiveConverter.reverseConvertToList(locomotiveMapper.getByConditions(search)));
        } else {
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
//    private List<LocomotiveVo> getLocomotiveVos(String coreIp, List<Locomotive> locomotives) {
//        List<LocomotiveVo> locomotiveVos = new ArrayList<>();
//        if (StringUtils.isNotEmpty(coreIp) && CollectionUtils.isNotEmpty(locomotives)) {
//            try {
//                CountDownLatch countDownLatch = new CountDownLatch(locomotives.size());
//                locomotives.stream()
//                        .forEach(locomotive -> {
//                            ThreadPoolUtils.getLocomotiveExecutor().execute(() -> {
//                                try {
//                                    LocomotiveVo locomotiveVo = queryLocomotiveMap(coreIp, locomotive);
//                                    if (Objects.nonNull(locomotiveVo)) {
//                                        locomotiveVos.add(locomotiveVo);
//                                    }
//                                } catch (Exception e) {
//                                    log.error("获取机车数据失败:{}", JSON.toJSONString(locomotive), e);
//                                } finally {
//                                    countDownLatch.countDown();
//                                }
//                            });
//                        });
//                countDownLatch.await();
//            } catch (Exception e) {
//                log.error("获取所有机车状态错误", e);
//            }
//        }
//        return locomotiveVos;
//    }

    /**
     * 获取机车数据以及机车状态
     *
     * @param coreNetIp
     * @param pageCount
     * @return
     */
    private Map<String, String> queryLocomotiveMap(String coreNetIp, Integer pageCount) {
        Map<String, String> result = new HashMap<>();
        try {
            if (pageCount > 1) {
                for (Integer currentPage = 2; currentPage <= pageCount; currentPage++) {
                    String htmlContent = HttpClientUtils.doGet(String.format(CORE_NETWORK_URL, coreNetIp, currentPage), Integer.parseInt(timeOut));
                    Map<String, String> ueNodeMap = parseHtmlContent(htmlContent);
                    if (MapUtils.isNotEmpty(ueNodeMap)) {
                        result.putAll(ueNodeMap);
                    }
                }
            }
        } catch (Exception e) {
            log.error("机车请求ip数据失败", e);
        }
        return result;
    }

    private Map<String, String> parseHtmlContent(String htmlContent) {
        Map<String, String> result = new HashMap<>();
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
                        result.put(ueIp, eNodeBIP);
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String htmlContent = "\n" +
                "\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>\n" +
                "    <title>IPLOOK Epc Web OMC</title>\n" +
                "    <link href=\"/static/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "    <link rel=\"stylesheet\" href=\"/static/css/styleNew.css\" type=\"text/css\" media=\"all\">\n" +
                "    \n" +
                "\n" +
                "</head> \n" +
                "\n" +
                "<body>\n" +
                "<nav class=\"navbar navbar-default navbar-fixed-top\">\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"navbar-header\">\n" +
                "            <img src=\"/static/images/logo/logo_3.png\" class=\"img-rounded\" alt=\"Responsive image\" style=\"height:50px\">\n" +
                "        </div>\n" +
                "        <div class=\"navbar-collapse collapse\">\n" +
                "            <ul class=\"nav navbar-nav navbar-right\">\n" +
                "                <li><a><span class=\"glyphicon glyphicon-user\"></span><span id=\"User\"></span></a></li>\n" +
                "                <li><a id=\"logout\" href=\"/\"><span class=\"glyphicon glyphicon-off\"></span>&nbsp;&nbsp;Logout</a></li>\n" +
                "            </ul>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</nav>\n" +
                "\n" +
                "<div class=\"container\">\n" +
                "    <nav class=\"navbar navbar-inverse\" style=\"background-color: #4aa9c3;\">\n" +
                "\n" +
                "        <div class=\"collapse navbar-collapse\">\n" +
                "            <ul class=\"nav navbar-nav\">\n" +
                "                <li class=\"dropdown\" style=\"min-width: 162px;\">\n" +
                "                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\"\n" +
                "                       aria-expanded=\"false\"><span class=\"glyphicon glyphicon-cog\"></span>&nbsp;&nbsp;Configuration</a>\n" +
                "                    <ul class=\"dropdown-menu\">\n" +
                "                        <li><a class=\"mme\" href=\"mmeConfigS1.html\">MME</a></li>\n" +
                "                        <li role=\"separator\" class=\"divider mme\"></li>\n" +
                "                        <li><a class=\"gw\" href=\"gwInterface.html\">GW</a></li>\n" +
                "                        <li role=\"separator\" class=\"divider gw\"></li>\n" +
                "                        <li><a class=\"hss\" href=\"hssConfig.html\">HSS</a></li>\n" +
                "                        <li role=\"separator\" class=\"divider hss\"></li>\n" +
                "                        <li><a href=\"configInterface.html\">System</a></li>\n" +
                "                    </ul>\n" +
                "                </li>\n" +
                "                <li class=\"dropdown\" style=\"min-width: 162px;\">\n" +
                "                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\"\n" +
                "                       aria-expanded=\"false\"><span class=\"glyphicon glyphicon-wrench\"></span>&nbsp;&nbsp;Maintenance</a>\n" +
                "                    <ul class=\"dropdown-menu\">\n" +
                "                        <li><a href=\"mmeMaintainance.html\">Basic Info</a></li>\n" +
                "                        <li role=\"separator\" class=\"divider\"></li>\n" +
                "                        <li><a class=\"mme\" href=\"subsStatus.html\">MME Subscriber Info</a></li>\n" +
                "                        <li role=\"separator\" class=\"divider mme\"></li>\n" +
                "                        <li><a class=\"gw\" href=\"mobileAddrDuration.html\">GW Subscriber Info</a></li>\n" +
                "                        <li role=\"separator\" class=\"divider gw\"></li>\n" +
                "                        <li><a href=\"epcCapture.html\">Capture</a></li>\n" +
                "                        <li role=\"separator\" class=\"divider\"></li>\n" +
                "                        <li><a class=\"mme\" href=\"mmeCalltrace.html\">Call Trace</a></li>\n" +
                "                        <!-- <li role=\"separator\" class=\"divider\"></li> -->\n" +
                "                        <!-- <li><a class=\"gw\" href=\"dedicatedBearer.html\">GW Dedicated Bearer</a></li> -->\n" +
                "                        <!-- <li role=\"separator\" class=\"divider\"></li> -->\n" +
                "                        <!-- <li><a class=\"gw\" href=\"imsiAddrPool.html\">GW Imsi Address Pool</a></li> -->\n" +
                "                        <li role=\"separator\" class=\"divider\"></li>\n" +
                "                        <li><a href=\"pingPacket.html\">Ping</a></li>\n" +
                "                    </ul>\n" +
                "                </li>\n" +
                "                <li class=\"dropdown\" style=\"min-width: 120px;\">\n" +
                "                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\"\n" +
                "                       aria-expanded=\"false\"><span class=\"glyphicon glyphicon-th-large\"></span>&nbsp;&nbsp;System</a>\n" +
                "                    <ul class=\"dropdown-menu\">\n" +
                "                        <li><a href=\"softwareInfo.html\">Software</a></li>\n" +
                "                        <li role=\"separator\" class=\"divider\"></li>\n" +
                "                        <li><a id=\"proMgmt\" href=\"mmeProcessMgmt.html\">System Mgmt</a></li>\n" +
                "                        <li role=\"separator\" class=\"divider\"></li>\n" +
                "                        <li><a href=\"licenseInfo.html\">License Mgmt</a></li>\n" +
                "                        <li role=\"separator\" class=\"divider\"></li>\n" +
                "                        <li><a class=\"administrator\" href=\"userMgmt.html\">User Mgmt</a></li>\n" +
                "                    </ul>\n" +
                "                </li>\n" +
                "                <li class=\"dropdown\" style=\"min-width: 120px;\">\n" +
                "                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\"\n" +
                "                       aria-expanded=\"false\"><span class=\"glyphicon glyphicon-list-alt\"></span>&nbsp;&nbsp;Statistics</a>\n" +
                "                    <ul class=\"dropdown-menu\">\n" +
                "                        <li><a class=\"mme\" href=\"mmeStatsUeRegistered.html\">MME KPI</a></li>\n" +
                "                        <li role=\"separator\" class=\"divider mme\"></li>\n" +
                "                        <li><a class=\"gw\" href=\"gwDataPlaneActivePPS.html\">GW KPI</a></li>\n" +
                "                    </ul>\n" +
                "                </li>\n" +
                "                <li class=\"dropdown\" style=\"min-width: 120px;\">\n" +
                "                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\"\n" +
                "                       aria-expanded=\"false\"><span class=\"glyphicon glyphicon-alert\"></span>&nbsp;&nbsp;Alarm</a>\n" +
                "                    <ul class=\"dropdown-menu\">\n" +
                "                        <li><a href=\"fmAlarm.html\">Alarm</a></li>\n" +
                "                    </ul>\n" +
                "                </li>\n" +
                "            </ul>\n" +
                "        </div>\n" +
                "\n" +
                "    </nav>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div class=\"container\">\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col-md-12\">\n" +
                "            <ul class=\"nav nav-tabs\">\n" +
                "                <li id=\"subsStatus\" role=\"presentation\"><a href=\"subsStatus.html\">Subs Status</a></li>\n" +
                "                <li id=\"contextOperation\" role=\"presentation\"><a href=\"subsContextOpreration.html\">Context Operation</a></li>\n" +
                "                <li id=\"ueinfo\" role=\"presentation\"><a href=\"UEInfo.html?action=load\">UE Info</a></li>\n" +
                "            </ul>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div class=\"container panelGap\">\n" +
                "    <div class=\"row\" style=\"padding-bottom:10px;\">\n" +
                "        <div class=\"col-md-2\"></div>\n" +
                "        <div class=\"col-md-8\">\n" +
                "            <table style=\"width:100%;\">\n" +
                "                <tbody>\n" +
                "                    <tr><td colspan=\"7\" style=\"text-align:right;\"><input id=\"reload-btn\" type=\"submit\" class=\"obdisabled btn btn-default\" onclick=\"reloadPage()\" value=\"Reload\"/></td></tr>\n" +
                "                    <tr><td colspan=\"7\" style=\"text-align:left;\">Query in the results：</td></tr>\n" +
                "                    <tr>\n" +
                "                        <form id=\"search\" action=\"UEInfo.html\" method=\"get\">\n" +
                "                            <td style=\"font-weight:bold;padding-right:5px;\">UE Ip</td>\n" +
                "                            <td><input autocomplete=\"off\" class=\"form-control\" id=\"ueIp\" name=\"ueIp\" style=\"width:80%;\" type=\"text\" /></td>\n" +
                "                            <td style=\"font-weight:bold;padding-right:5px;\">Msisdn</td>\n" +
                "                            <td><input autocomplete=\"off\" class=\"form-control\" id=\"msisdn\" name=\"msisdn\" style=\"width:80%;\" type=\"text\" /></td>\n" +
                "                            <td style=\"font-weight:bold;padding-right:5px;\">eNodeB IP</td>\n" +
                "                            <td><input autocomplete=\"off\" class=\"form-control\" id=\"enodebIp\" name=\"enodebIp\" style=\"width:80%;\" type=\"text\" /></td>\n" +
                "                            <td><input id=\"search-btn\" type=\"submit\" onclick=\"searchBtnClick()\" class=\"obdisabled btn btn-default\" value=\"Query\"/></td>\n" +
                "                            <input type=\"hidden\" name=\"registed\" value=\"11\" />\n" +
                "                            <input type=\"hidden\" name=\"online\" value=\"10\" />\n" +
                "                        </form>\n" +
                "                    </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "        <div class=\"col-md-2\"></div>\n" +
                "    </div>\n" +
                "\t<div class=\"row\">\n" +
                "\t\t<div class=\"col-md-2\"></div>\n" +
                "\t\t<div class=\"col-md-8\">\n" +
                "\t\t\t<div class=\"panel panel-info\">\n" +
                "\t\t\t\t<div class=\"panel-heading\">\n" +
                "\t\t\t\t\t<h3 class=\"panel-title\">UE</h3>\n" +
                "\t\t\t\t</div>\n" +
                "                <div class=\"panel-body\">\n" +
                "                    <div style=\"width:100%;text-align:right;padding-bottom:10px;\">Total number of terminals<b>11</b>, Total online<b>10</b></div>\n" +
                "                    <table  class=\"table table-bordered\">\n" +
                "                        <thead><th>Ue IP</th><th>Status</th><th>Msisdn</th><th>eNodeB IP</th></thead>\n" +
                "                        \n" +
                "                            <tbody>\n" +
                "                                \n" +
                "                                    <tr>\n" +
                "                                        <td>172.20.80.23</td>\n" +
                "                                        <td>\n" +
                "                                            \n" +
                "                                                <span></span>\n" +
                "                                            \n" +
                "                                            connected\n" +
                "                                        </td>\n" +
                "                                        <td>18888888888</td>\n" +
                "                                        <td>172.16.11.114</td>\n" +
                "                                    </tr>\n" +
                "                                \n" +
                "                                    <tr>\n" +
                "                                        <td>172.20.80.25</td>\n" +
                "                                        <td>\n" +
                "                                            \n" +
                "                                                <span></span>\n" +
                "                                            \n" +
                "                                            connected\n" +
                "                                        </td>\n" +
                "                                        <td>18888888888</td>\n" +
                "                                        <td>172.16.11.109</td>\n" +
                "                                    </tr>\n" +
                "                                \n" +
                "                                    <tr>\n" +
                "                                        <td>172.20.80.28</td>\n" +
                "                                        <td>\n" +
                "                                            \n" +
                "                                                <span></span>\n" +
                "                                            \n" +
                "                                            connected\n" +
                "                                        </td>\n" +
                "                                        <td>18888888888</td>\n" +
                "                                        <td>172.16.11.113</td>\n" +
                "                                    </tr>\n" +
                "                                \n" +
                "                                    <tr>\n" +
                "                                        <td></td>\n" +
                "                                        <td>\n" +
                "                                            \n" +
                "                                            detached\n" +
                "                                        </td>\n" +
                "                                        <td></td>\n" +
                "                                        <td></td>\n" +
                "                                    </tr>\n" +
                "                                \n" +
                "                                    <tr>\n" +
                "                                        <td>172.20.80.5</td>\n" +
                "                                        <td>\n" +
                "                                            \n" +
                "                                                <span></span>\n" +
                "                                            \n" +
                "                                            connected\n" +
                "                                        </td>\n" +
                "                                        <td>18888888888</td>\n" +
                "                                        <td>172.16.11.106</td>\n" +
                "                                    </tr>\n" +
                "                                \n" +
                "                                    <tr>\n" +
                "                                        <td></td>\n" +
                "                                        <td>\n" +
                "                                            \n" +
                "                                            detached\n" +
                "                                        </td>\n" +
                "                                        <td>18888888888</td>\n" +
                "                                        <td>0.0.0.0</td>\n" +
                "                                    </tr>\n" +
                "                                \n" +
                "                                    <tr>\n" +
                "                                        <td>172.20.80.118</td>\n" +
                "                                        <td>\n" +
                "                                            \n" +
                "                                                <span></span>\n" +
                "                                            \n" +
                "                                            connected\n" +
                "                                        </td>\n" +
                "                                        <td>18888888818</td>\n" +
                "                                        <td>172.16.11.115</td>\n" +
                "                                    </tr>\n" +
                "                                \n" +
                "                                    <tr>\n" +
                "                                        <td>172.20.80.12</td>\n" +
                "                                        <td>\n" +
                "                                            \n" +
                "                                                <span></span>\n" +
                "                                            \n" +
                "                                            connected\n" +
                "                                        </td>\n" +
                "                                        <td>18888888812</td>\n" +
                "                                        <td>172.16.11.115</td>\n" +
                "                                    </tr>\n" +
                "                                \n" +
                "                                    <tr>\n" +
                "                                        <td>172.20.80.121</td>\n" +
                "                                        <td>\n" +
                "                                            \n" +
                "                                            idle\n" +
                "                                        </td>\n" +
                "                                        <td>18888888888</td>\n" +
                "                                        <td>172.16.11.114</td>\n" +
                "                                    </tr>\n" +
                "                                \n" +
                "                                    <tr>\n" +
                "                                        <td>172.20.80.122</td>\n" +
                "                                        <td>\n" +
                "                                            \n" +
                "                                            idle\n" +
                "                                        </td>\n" +
                "                                        <td>18888888888</td>\n" +
                "                                        <td>172.16.11.113</td>\n" +
                "                                    </tr>\n" +
                "                                \n" +
                "                            </tbody>\n" +
                "                        \n" +
                "                    </table>\n" +
                "                    <nav class=\"pull-right\" aria-label=\"Page navigation\" style=\"margin-right: 0%;\">\n" +
                "                            <ul id=\"ue-list\" class=\"pagination\">\n" +
                "                                \n" +
                "                                \n" +
                "                                    \n" +
                "                                        <li class=\"active\">\n" +
                "                                            <a href=\"?page=1&registed=11&online=10&enodebIp=&ueIp=&msisdn=\" onclick=\"paginationClick()\">1</a>\n" +
                "                                        </li>\n" +
                "                                    \n" +
                "                                \n" +
                "                                    \n" +
                "                                        <li>\n" +
                "                                            <a href=\"?page=2&registed=11&online=10&enodebIp=&ueIp=&msisdn=\" onclick=\"paginationClick()\">2</a>\n" +
                "                                        </li>\n" +
                "                                    \n" +
                "                                \n" +
                "                                \n" +
                "                                    <li>\n" +
                "                                        <a href=\"?page=2&registed=11&online=10&enodebIp=&ueIp=&msisdn=\" id=\"next-btn\" onclick=\"paginationClick()\" aria-label=\"Next\">\n" +
                "                                            <span aria-hidden=\"true\">&raquo;</span>\n" +
                "                                        </a>\n" +
                "                                    </li>\n" +
                "                                \n" +
                "                            </ul>\n" +
                "                        </nav>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\t\t<div class=\"col-md-2\"></div>\n" +
                "\t</div>\n" +
                "    <input type=\"hidden\" id=\"registedUeNum\" name=\"registedUeNum\" value=\"11\">\n" +
                "    <input type=\"hidden\" id=\"result\" name=\"result\" value=\"\">\n" +
                "    <input type=\"hidden\" id=\"desc\" name=\"desc\" value=\"\">\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "<div style=\"width:1140px;height:34px;\"></div>\n" +
                "<nav class=\"navbar navbar-default navbar-fixed-bottom\">\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"center-block footer\">\n" +
                "            <span id=\"brand\">IPLOOK Network Technologies CO.,LTD © 2020</span>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</nav>\n" +
                "<script src=\"/static/js/jquery-1.10.2/jquery.min.js\"></script>\n" +
                "<script src=\"/static/js/jquery-form/jquery.form.min.js\"></script>\n" +
                "<script src=\"/static/js/jquery.cookie.js\" type=\"text/javascript\" charset=\"utf-8\"></script>\n" +
                "<script src=\"/static/js/epc/main.js\"></script>\n" +
                "<script src=\"/static/js/bootstrap.min.js\"></script>\n" +
                "<script type=\"text/javascript\">\n" +
                "    $('.nav .dropdown').mouseenter(function(){\n" +
                "        $(this).addClass('open');\n" +
                "    });\n" +
                "\n" +
                "    $('.nav .dropdown').mouseleave(function(){\n" +
                "        $(this).removeClass('open');\n" +
                "    });\n" +
                "    $(document).ready(function () {\n" +
                "        var userName = $.cookie(\"login_userName\");\n" +
                "        if (userName == null || userName == \"LOG_OUT\") {\n" +
                "            location.href = \"index.html\";\n" +
                "        }\n" +
                "        else {\n" +
                "            userType = $.cookie(\"login_userType\");\n" +
                "            var deviceType = $.cookie(\"deviceType\");\n" +
                "            getDetailHtml(userName, userType, deviceType);\n" +
                "        }\n" +
                "    });\n" +
                "    document.onkeypress=function(e){\n" +
                "        var code;\n" +
                "        if (!e)\n" +
                "        {\n" +
                "            var e = window.event;\n" +
                "        }\n" +
                "        if(e.keyCode){\n" +
                "            code = e.keyCode;\n" +
                "        }\n" +
                "        else if(e.which){\n" +
                "            code = e.which\n" +
                "        }\n" +
                "        if(code == 13)\n" +
                "        {\n" +
                "            return false;\n" +
                "        }\n" +
                "    }\n" +
                "</script>\n" +
                "\n" +
                "<script src=\"/static/js/epc/UEInfo.js\"></script>\n" +
                "<script>\n" +
                "    $.ajaxSetup({\n" +
                "        data: {\n" +
                "            csrfmiddlewaretoken: 'm0gh8qTFZnsakJLXmC3rlsWXyMkpbIwX'\n" +
                "        },\n" +
                "    });\n" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        Integer page = 0;
        if (StringUtils.isNotEmpty(htmlContent)) {
            Document doc = Jsoup.parse(htmlContent);
            Element ulRow = doc.select("ul[class=pagination]").get(0);
            Elements elements = ulRow.select("li");
            page = elements.size() - 1;
        }
        System.out.println(111);
    }

    private Integer getPageCountFromHtmlContent(String htmlContent) {
        if (StringUtils.isNotEmpty(htmlContent)) {
            Document doc = Jsoup.parse(htmlContent);
            Element ulRow = doc.select("ul[class=pagination]").get(0);
            if (Objects.isNull(ulRow)) {
                return 0;
            }
            Elements elements = ulRow.select("li");
            if (Objects.isNull(elements) || elements.size() <= 2) {
                return 0;
            }
            return elements.size() - 1;
        }
        return 0;
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
