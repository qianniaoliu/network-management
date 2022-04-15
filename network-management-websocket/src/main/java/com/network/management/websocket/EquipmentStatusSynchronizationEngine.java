/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.network.management.websocket;

import com.network.management.domain.vo.BordInformationAggregation;
import com.network.management.domain.vo.DeviceStatusVo;
import com.network.management.service.BordInformationService;
import com.network.management.service.EquipmentService;
import com.network.management.websocket.event.EquipmentStatusEvent;
import com.network.management.websocket.event.EquipmentStatusEventPublisher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author shenlong
 */
@Component
public class EquipmentStatusSynchronizationEngine implements InitializingBean {

    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

    private ExecutorService equipmentStatusQueryExecutorService = Executors.newFixedThreadPool(4);

    private final ConcurrentMap<Integer, DeviceStatusVo<?>> deviceStatusMapping = new ConcurrentHashMap<>();

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private BordInformationService bordInformationService;

    @Autowired
    private EquipmentStatusEventPublisher publisher;

    public void start() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            BordInformationAggregation bordInformationAggregation = bordInformationService.getAll();
            CountDownLatch countDownLatch = new CountDownLatch(bordInformationAggregation.getEquipments().size());
            bordInformationAggregation.getEquipments().forEach(equipment -> {
                equipmentStatusQueryExecutorService.execute(() -> {
                    Integer equipmentId = equipment.getId();
                    DeviceStatusVo<?> deviceStatusVo = equipmentService.queryStatus(equipmentId);
                    if (deviceStatusMapping.containsKey(equipment.getId())) {
                        DeviceStatusVo<?> existDeviceStatusVo = deviceStatusMapping.get(equipmentId);
                        if (Objects.equals(deviceStatusVo, existDeviceStatusVo)) {
                            deviceStatusMapping.remove(equipmentId);
                        } else {
                            deviceStatusMapping.put(equipment.getId(), deviceStatusVo);
                        }
                    } else {
                        deviceStatusMapping.put(equipment.getId(), deviceStatusVo);
                    }
                    countDownLatch.countDown();
                });
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
            }
            if (!deviceStatusMapping.isEmpty()) {
                //当所有基站信息都查询完成后，基站信息推送给前端进行展示
                publisher.publish(new EquipmentStatusEvent(deviceStatusMapping.values()));
            }
        }, 1, 10, TimeUnit.SECONDS);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }
}