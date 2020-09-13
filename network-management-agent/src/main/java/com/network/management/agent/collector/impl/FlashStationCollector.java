package com.network.management.agent.collector.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.network.management.agent.collector.Collector;
import com.network.management.bo.DataBo;
import com.network.management.bo.DeviceBo;
import com.network.management.bo.FlashStationStatusBo;
import com.network.management.common.exception.BizException;
import com.network.management.common.exception.ErrorCodeEnum;
import com.network.management.common.httpclient.HttpClientUtils;
import com.network.management.enums.FlashStationKeyEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * flash界面基站状态数据采集器
 * @author yyc
 * @date 2020/9/12 22:03
 */
@Component("flashStationCollector")
@Slf4j
public class FlashStationCollector implements Collector{
    private static final String URL = "http://%s:8080/cgi-bin/c2s";
    /**
     * 请求参数key
     */
    private static final String BODY_MESSAGE_TYPE = "MESSAGETYPE";
    private static final String BODY_PARAMETER_TYPE = "PARAMETERTYPE";

    private static final Integer MESSAGE_TYPE_VALUE = 5;
    private static final Integer PARAMETER_TYPE_VALUE = 109;

    private static final String RESPONSE_KEY = "RESULT";
    @Value("${collect.time.out:10000}")
    private String timeOut;
    @Override
    public DataBo<?> collect(DeviceBo deviceBo) {
        Assert.notNull(deviceBo, "flash界面基站基本信息不能为空.");
        Assert.notNull(deviceBo.getIp(), "flash界面基站ip信息不能为空.");
        Assert.notNull(deviceBo.getEquipmentType(), "设备类型信息不能为空.");
        try {
            String result = HttpClientUtils.doPost(String.format(URL, deviceBo.getIp()), getBodyJson(), Integer.parseInt(timeOut));
            return parseResponse(result, deviceBo);
        } catch (Exception e) {
            log.error("flash界面基站http请求状态数据失败", e);
            throw new BizException(ErrorCodeEnum.HTTPCLIENT_ERROR);
        }
    }

    /**
     * 解析返回结果
     * @param result
     * @return
     */
    private DataBo<FlashStationStatusBo> parseResponse(String result, DeviceBo deviceBo) {
        if(StringUtils.isNotEmpty(result)){
            JSONObject jsonObject = JSON.parseObject(result);
            if(Objects.nonNull(jsonObject) && !jsonObject.isEmpty()){
                JSONObject statusJson = jsonObject.getJSONObject(RESPONSE_KEY);
                if(Objects.nonNull(statusJson) && !statusJson.isEmpty()){
                    return getDataBo(statusJson, deviceBo);
                }
            }
        }
        return null;
    }

    /**
     * 获取基站状态数据对象
     * @param statusJson {@link DataBo<FlashStationStatusBo>}
     * @param deviceBo {@link DeviceBo}
     * @return {@link DataBo<FlashStationStatusBo>}
     */
    private DataBo<FlashStationStatusBo> getDataBo(JSONObject statusJson, DeviceBo deviceBo) {
        DataBo<FlashStationStatusBo> dataBo = new DataBo<FlashStationStatusBo>();
        dataBo.setIp(deviceBo.getIp());
        dataBo.setType(deviceBo.getEquipmentType());
        FlashStationStatusBo stationStatusBo = new FlashStationStatusBo();
        stationStatusBo.setWanStatus(statusJson.getString(FlashStationKeyEnum.WAN_STATUS.getKey()));
        stationStatusBo.setWanInternet(statusJson.getString(FlashStationKeyEnum.WAN_INTERNET.getKey()));
        stationStatusBo.setIpSecSwitch(statusJson.getString(FlashStationKeyEnum.IP_SPEC_SWITCH.getKey()));
        stationStatusBo.setIpSecStatus(statusJson.getString(FlashStationKeyEnum.IP_SPEC_STATUS.getKey()));
        stationStatusBo.setS1Status(statusJson.getString(FlashStationKeyEnum.S1_STATUS.getKey()));
        stationStatusBo.setCellStatus(statusJson.getString(FlashStationKeyEnum.CELL_STATUS.getKey()));
        dataBo.setDataObj(stationStatusBo);
        return dataBo;
    }

    /**
     * 获取请求参数json
     * @return
     */
    private String getBodyJson(){
        Map<String, Integer> bodyMap = new HashMap<>();
        bodyMap.put(BODY_MESSAGE_TYPE, MESSAGE_TYPE_VALUE);
        bodyMap.put(BODY_PARAMETER_TYPE, PARAMETER_TYPE_VALUE);
        return JSON.toJSONString(bodyMap);
    }
}
