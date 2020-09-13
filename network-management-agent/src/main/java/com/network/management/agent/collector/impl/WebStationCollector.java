package com.network.management.agent.collector.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.network.management.agent.collector.Collector;
import com.network.management.bo.DataBo;
import com.network.management.bo.DeviceBo;
import com.network.management.bo.WebStationStatusBo;
import com.network.management.common.exception.BizException;
import com.network.management.common.exception.ErrorCodeEnum;
import com.network.management.common.httpclient.HttpClientUtils;
import com.network.management.enums.WebStationKeyEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * web界面基站状态数据采集器
 *
 * @author yyc
 * @date 2020/9/12 22:04
 */
@Component("webStationCollector")
@Slf4j
public class WebStationCollector implements Collector {
    /**
     * 请求url
     */
    private static final String URL = "http://%s:8080/page/core_diagnose.asp";
    private static final String RESPONSE_KEY = "data";
    /**
     * 匹配数据的正则表达式
     */
    private static final String PATTERN_KEY = "var jsonStr =.*;";
    /**
     * document过滤条件
     */
    private static final String DOCUMENT_FILTER_KEY = "javascript,script";

    @Value("${collect.time.out:10000}")
    private String timeOut;

    @Override
    public DataBo<?> collect(DeviceBo deviceBo) {
        Assert.notNull(deviceBo, "web界面基站基本信息不能为空.");
        Assert.notNull(deviceBo.getIp(), "web界面基站ip信息不能为空.");
        Assert.notNull(deviceBo.getEquipmentType(), "设备类型信息不能为空.");
        try {
            String result = HttpClientUtils.doGet(String.format(URL, deviceBo.getIp()), Integer.parseInt(timeOut));
            return getDataBo(getJSONObject(result), deviceBo);
        } catch (Exception e) {
            log.error("web界面基站http请求状态数据失败", e);
            throw new BizException(ErrorCodeEnum.HTTPCLIENT_ERROR);
        }
    }

    /**
     * 获取数据JSONObject
     * @param result
     * @return {@link JSONObject}
     */
    private JSONObject getJSONObject(String result) {
        if (StringUtils.isNotEmpty(result)) {
            Document doc = Jsoup.parse(result);
            Elements scriptElements = doc.select(DOCUMENT_FILTER_KEY);
            for (Element scriptElement : scriptElements) {
                if (scriptElement.childNodeSize() > 0) {
                    List<Node> nodes = scriptElement.childNodes();
                    if (!CollectionUtils.isEmpty(nodes)) {
                        Pattern pattern = Pattern.compile(PATTERN_KEY);
                        Matcher math = pattern.matcher(nodes.get(0).attr(RESPONSE_KEY));
                        if (math.find()) {
                            String dataStr = math.group();
                            return JSON.parseObject(dataStr.substring(dataStr.indexOf("=") + 3, dataStr.length() - 2));
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取设备状态数据对象
     * @param jsonObject {@link JSONObject}
     * @param deviceBo {@link DeviceBo}
     * @return {@link DataBo<WebStationStatusBo>}
     */
    private DataBo<WebStationStatusBo> getDataBo(JSONObject jsonObject, DeviceBo deviceBo){
        if(Objects.nonNull(jsonObject) && !jsonObject.isEmpty()){
            DataBo<WebStationStatusBo> dataBo = new DataBo<WebStationStatusBo>();
            dataBo.setIp(deviceBo.getIp());
            dataBo.setType(deviceBo.getEquipmentType());
            WebStationStatusBo webStationStatusBo = new WebStationStatusBo();
            webStationStatusBo.setRfStatus(jsonObject.getString(WebStationKeyEnum.RF_STATUS.getKey()));
            webStationStatusBo.setSctpStatus(jsonObject.getString(WebStationKeyEnum.SCTP_STATUS.getKey()));
            webStationStatusBo.setIpSpecStatus(jsonObject.getString(WebStationKeyEnum.IP_SPEC_STATUS.getKey()));
            webStationStatusBo.setCellStatus(jsonObject.getString(WebStationKeyEnum.CELL_STATUS.getKey()));
            webStationStatusBo.setWanStatus(jsonObject.getString(WebStationKeyEnum.WAN_STATUS.getKey()));
            webStationStatusBo.setNetManagerStatus(jsonObject.getString(WebStationKeyEnum.MANAGEMENT_STATUS.getKey()));
            webStationStatusBo.setTimeClockStatus(jsonObject.getString(WebStationKeyEnum.TIME_CLOCK_STATUS.getKey()));
            webStationStatusBo.setApStatus(jsonObject.getString(WebStationKeyEnum.AP_STATUS.getKey()));
            webStationStatusBo.setC820Status(jsonObject.getString(WebStationKeyEnum.C820_STATUS.getKey()));
            dataBo.setDataObj(webStationStatusBo);
            return dataBo;
        }
        return null;
    }
}
