package com.network.management.agent.Monitor.impl;

import com.alibaba.fastjson.JSON;
import com.network.management.agent.Monitor.Monitor;
import com.network.management.agent.annotation.DeviceType;
import com.network.management.agent.collector.Collector;
import com.network.management.agent.collector.impl.OtherDeviceCollector;
import com.network.management.agent.hanlder.DataHandler;
import com.network.management.agent.hanlder.impl.OtherDeviceHandler;
import com.network.management.bo.DataBo;
import com.network.management.bo.DeviceBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 其他设备状态数据监控
 * @author yyc
 * @date 2020/9/12 22:36
 */
@Component
@DeviceType("otherDevice")
@Slf4j
public class OtherDeviceMonitor implements Monitor{
    @Autowired
    private OtherDeviceCollector collector;
    @Autowired
    private OtherDeviceHandler handler;
    @Override
    public void monitoring(DeviceBo deviceBo) {
        Assert.notNull(deviceBo, "其他设备基本信息不能为空.");
        Assert.notNull(deviceBo.getIp(), "其他设备ip信息不能为空.");
        Assert.notNull(deviceBo.getEquipmentType(), "设备类型信息不能为空.");
        DataBo<?> dataBo = collector.collect(deviceBo);
        if(Objects.nonNull(dataBo)){
            handler.handle(dataBo);
        }else {
            log.error("device collect data is null:" + JSON.toJSONString(deviceBo));
        }
    }
}
