package com.network.management.agent.Monitor.impl;

import com.network.management.agent.Monitor.Monitor;
import com.network.management.agent.annotation.DeviceType;
import com.network.management.agent.collector.Collector;
import com.network.management.agent.hanlder.DataHandler;
import com.network.management.bo.DeviceBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * web界面基站状态数据监控
 * @author yyc
 * @date 2020/9/12 22:36
 */
@Component
@DeviceType("webStation")
public class WebStationMonitor implements Monitor{
    @Resource(name = "webStationCollector")
    private Collector collector;
    @Resource(name = "webStationHandler")
    private DataHandler handler;
    @Override
    public void monitoring(DeviceBo deviceBo) {

    }
}
