package com.network.management.agent.collector;

import com.network.management.bo.DataBo;
import com.network.management.bo.DeviceBo;
import org.springframework.stereotype.Component;

/**
 * 其他设备状态采集器
 * @author yyc
 * @date 2020/9/12 22:05
 */
@Component("otherDeviceCollector")
public class OtherDeviceCollector implements Collector{
    @Override
    public DataBo<?> collect(DeviceBo deviceBo) {
        return null;
    }
}
