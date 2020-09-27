package com.network.management.service;

import com.google.common.collect.Lists;
import com.network.management.domain.bo.DeviceStatusResultBo;
import com.network.management.domain.search.EquipmentStatusSearch;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author yusheng
 */
@Component
public class DeviceStatueHandler implements ApplicationContextAware {

    private final List<DeviceStatusStrategy> deviceStatusStrategyCache = Lists.newArrayList();

    public DeviceStatusResultBo search(EquipmentStatusSearch search){
        for(DeviceStatusStrategy deviceStatusStrategy : deviceStatusStrategyCache){
            if(deviceStatusStrategy.support(search.getEquipmentType())){
                return deviceStatusStrategy.search(search);
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, DeviceStatusStrategy> deviceStatusStrategyMap = applicationContext.getBeansOfType(DeviceStatusStrategy.class);
        deviceStatusStrategyCache.addAll(deviceStatusStrategyMap.values());
    }
}
