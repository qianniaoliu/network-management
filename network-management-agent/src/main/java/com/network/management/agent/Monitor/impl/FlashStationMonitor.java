package com.network.management.agent.Monitor.impl;

import com.network.management.agent.Monitor.Monitor;
import com.network.management.agent.annotation.DeviceType;
import com.network.management.agent.collector.Collector;
import com.network.management.agent.hanlder.DataHandler;
import com.network.management.bo.DeviceBo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * flash界面基站状态数据监控
 * @author yyc
 * @date 2020/9/12 22:34
 */
@Component
@DeviceType("flashStation")
public class FlashStationMonitor implements Monitor{
    @Resource(name = "flashStationCollector")
    private Collector collector;
    @Resource(name = "flashStationHandler")
    private DataHandler handler;
    @Override
    public void monitoring(DeviceBo deviceBo) {

    }
}
