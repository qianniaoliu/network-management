package com.network.management.agent.Monitor;

import com.network.management.agent.annotation.DeviceType;
import com.network.management.bo.DeviceBo;
import org.springframework.stereotype.Component;

/**
 * flash界面基站状态数据监控
 * @author yyc
 * @date 2020/9/12 22:34
 */
@Component
@DeviceType("flashDevice")
public class FlashStationMonitor implements Monitor{
    @Override
    public void monitoring(DeviceBo deviceBo) {

    }
}
