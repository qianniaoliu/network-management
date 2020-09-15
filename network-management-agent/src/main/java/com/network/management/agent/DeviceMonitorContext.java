package com.network.management.agent;

import com.network.management.agent.Monitor.Monitor;
import com.network.management.agent.annotation.DeviceCollectorType;
import com.network.management.agent.annotation.DeviceType;
import com.network.management.agent.collector.Collector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 设备监控上下文
 *
 * @author yyc
 * @date 2020/9/12 23:10
 */
@Component
@Slf4j
public class DeviceMonitorContext implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * key为device type，value为class
     */
    private static final Map<String, Class> deviceMonitorMap = new HashMap<>(10);

    /**
     * key为device type，value为class
     */
    private static final Map<String, Class> deviceCollectorMap = new HashMap<>(10);

    /**
     * 获取Monitor对象实例
     * @param type 设备类型
     * @return
     */
    public Monitor getMonitor(String type) {
        Class clazz = deviceMonitorMap.get(type);
        if (clazz == null) {
            log.error("not support device monitor type:{}", type);
            return null;
        }
        return (Monitor) applicationContext.getBean(clazz);
    }

    /**
     * 获取Collector对象实例
     * @param type 设备类型
     * @return
     */
    public Collector getCollector(String type){
        Class clazz = deviceCollectorMap.get(type);
        if (clazz == null) {
            log.error("not support device collector type:{}", type);
            return null;
        }
        return (Collector) applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        fullMonitorClassMap(applicationContext);
        fullCollectorClassMap(applicationContext);

    }

    /**
     * 填充Monitor Class Map
     * @param applicationContext {@link ApplicationContext}
     */
    private void fullMonitorClassMap(ApplicationContext applicationContext) {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(DeviceType.class);
        if (Objects.nonNull(beans) && !beans.isEmpty()) {
            for (Object deviceBean : beans.values()) {
                String deviceType = deviceBean.getClass().getAnnotation(DeviceType.class).value();
                deviceMonitorMap.put(deviceType, deviceBean.getClass());
            }
        }
    }

    /**
     * 填充Collector Class Map
     * @param applicationContext {@link ApplicationContext}
     */
    private void fullCollectorClassMap(ApplicationContext applicationContext) {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(DeviceCollectorType.class);
        if (Objects.nonNull(beans) && !beans.isEmpty()) {
            for (Object deviceBean : beans.values()) {
                String deviceCollectorType = deviceBean.getClass().getAnnotation(DeviceCollectorType.class).value();
                deviceCollectorMap.put(deviceCollectorType, deviceBean.getClass());
            }
        }
    }
}
