package com.network.management.agent.Monitor;

import com.network.management.agent.annotation.DeviceType;
import com.network.management.bo.DeviceBo;
import org.springframework.stereotype.Component;

/**
 * 其他设备状态数据监控
 * @author yyc
 * @date 2020/9/12 22:36
 */
@Component
@DeviceType("otherDevice")
public class OtherDeviceMonitor implements Monitor{
    @Override
    public void monitoring(DeviceBo deviceBo) {

    }
}
