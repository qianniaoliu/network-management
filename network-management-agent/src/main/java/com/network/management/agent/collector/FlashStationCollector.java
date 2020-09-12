package com.network.management.agent.collector;

import com.network.management.bo.DataBo;
import com.network.management.bo.DeviceBo;
import org.springframework.stereotype.Component;

/**
 * flash界面基站状态数据采集器
 * @author yyc
 * @date 2020/9/12 22:03
 */
@Component("flashStationCollector")
public class FlashStationCollector implements Collector{
    @Override
    public DataBo<?> collect(DeviceBo deviceBo) {
        return null;
    }
}
