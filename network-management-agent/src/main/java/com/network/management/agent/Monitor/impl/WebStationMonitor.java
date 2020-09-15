package com.network.management.agent.Monitor.impl;

import com.alibaba.fastjson.JSON;
import com.network.management.agent.Monitor.Monitor;
import com.network.management.agent.annotation.DeviceType;
import com.network.management.agent.collector.impl.WebStationCollector;
import com.network.management.agent.hanlder.impl.StationHandler;
import com.network.management.domain.bo.DataBo;
import com.network.management.domain.bo.DeviceBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * 瑞斯康达基站状态数据监控
 *
 * @author yyc
 * @date 2020/9/12 22:36
 */
@Component
@DeviceType("webStation")
@Slf4j
public class WebStationMonitor implements Monitor {
    @Autowired
    private WebStationCollector collector;
    @Autowired
    private StationHandler handler;

    @Override
    public void monitoring(DeviceBo deviceBo) {
        Assert.notNull(deviceBo, "web界面基站基本信息不能为空.");
        Assert.notNull(deviceBo.getIp(), "web界面基站ip信息不能为空.");
        Assert.notNull(deviceBo.getEquipmentType(), "设备类型信息不能为空.");
        DataBo<?> dataBo = collector.collect(deviceBo);
        if(Objects.nonNull(dataBo)){
            handler.handle(dataBo);
        }else {
            log.error("device collect data is null:" + JSON.toJSONString(deviceBo));
        }
    }
}
