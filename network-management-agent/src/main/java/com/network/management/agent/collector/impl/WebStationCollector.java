package com.network.management.agent.collector.impl;

import com.network.management.agent.collector.Collector;
import com.network.management.bo.DataBo;
import com.network.management.bo.DeviceBo;
import org.springframework.stereotype.Component;

/**
 * web界面基站状态数据采集器
 * @author yyc
 * @date 2020/9/12 22:04
 */
@Component("webStationCollector")
public class WebStationCollector implements Collector{
    @Override
    public DataBo<?> collect(DeviceBo deviceBo) {
        return null;
    }
}
