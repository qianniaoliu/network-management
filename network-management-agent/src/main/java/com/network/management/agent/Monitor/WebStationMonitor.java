package com.network.management.agent.Monitor;

import com.network.management.agent.annotation.DeviceType;
import com.network.management.bo.DeviceBo;
import org.springframework.stereotype.Component;

/**
 * web界面基站状态数据监控
 * @author yyc
 * @date 2020/9/12 22:36
 */
@Component
@DeviceType("webStation")
public class WebStationMonitor implements Monitor{
    @Override
    public void monitoring(DeviceBo deviceBo) {

    }
}
