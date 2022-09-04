/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.network.management.websocket;

import com.network.management.domain.dao.Equipment;
import com.network.management.domain.enums.PowerCmdEnum;
import com.network.management.domain.vo.BordInformationAggregation;
import com.network.management.domain.vo.DeviceStatusVo;
import com.network.management.domain.vo.PowerReqVo;
import com.network.management.domain.vo.PowerStatusVo;
import com.network.management.service.BordInformationService;
import com.network.management.service.EquipmentService;
import com.network.management.service.PowerStatusService;
import com.network.management.websocket.event.EquipmentStatusEvent;
import com.network.management.websocket.event.EquipmentStatusEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
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
@Slf4j
public class EquipmentStatusSynchronizationEngine implements InitializingBean {

    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

    private ExecutorService equipmentStatusQueryExecutorService = Executors.newFixedThreadPool(4);

    private final ConcurrentMap<Integer, EquipmentStatusCombination> equipmentStatusCombinationMapping = new ConcurrentHashMap<>();

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private BordInformationService bordInformationService;

    @Autowired
    private EquipmentStatusEventPublisher publisher;

    @Autowired
    private PowerStatusService powerStatusService;

    public void start() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.info("基站状态推送定时任务开始执行时间：{}", dateFormat.format(new Date()));
            BordInformationAggregation bordInformationAggregation = bordInformationService.getAll();
            if (CollectionUtils.isNotEmpty(bordInformationAggregation.getEquipments())) {
                CountDownLatch countDownLatch = new CountDownLatch(bordInformationAggregation.getEquipments().size());
                bordInformationAggregation.getEquipments().forEach(equipment -> {
                    equipmentStatusQueryExecutorService.execute(() -> doQueryEquipmentStatus(countDownLatch, equipment));
                });
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                }
                log.info("基站状态推送定时任务执行结束时间：{}", dateFormat.format(new Date()));
                if (!equipmentStatusCombinationMapping.isEmpty()) {
                    log.info("当前基站状态信息推送个数：{}", equipmentStatusCombinationMapping.size());
                    //当所有基站信息都查询完成后，基站信息推送给前端进行展示
                    publisher.publish(new EquipmentStatusEvent(equipmentStatusCombinationMapping.values()));
                }
            }
        }, 1, 60, TimeUnit.SECONDS);
    }

    /**
     * 查询设备状态
     *
     * @param countDownLatch
     * @param equipment
     */
    private void doQueryEquipmentStatus(CountDownLatch countDownLatch, Equipment equipment) {
        try {
            Integer equipmentId = equipment.getId();
            DeviceStatusVo<?> deviceStatusVo = equipmentService.queryStatus(equipmentId);
            PowerStatusVo powerStatusVo = queryPowerStatus(equipment);
            EquipmentStatusCombination equipmentStatusCombination = new EquipmentStatusCombination(equipmentId, deviceStatusVo,
                    powerStatusVo);
            if (equipmentStatusCombinationMapping.containsKey(equipment.getId())) {
                EquipmentStatusCombination existEquipmentStatusCombination = equipmentStatusCombinationMapping.get(equipmentId);
                if (Objects.equals(equipmentStatusCombination, existEquipmentStatusCombination)) {
                    equipmentStatusCombinationMapping.remove(equipmentId);
                } else {
                    equipmentStatusCombinationMapping.put(equipment.getId(), equipmentStatusCombination);
                }
            } else {
                equipmentStatusCombinationMapping.put(equipment.getId(), equipmentStatusCombination);
            }
        } catch (Exception ex) {
            log.error("定时任务查询基站状态失败,基站id：{}", equipment.getId());
        } finally {
            countDownLatch.countDown();
        }
    }

    /**
     * 查询电源状态
     *
     * @param equipment 设备信息
     * @return {@link PowerStatusVo}
     */
    private PowerStatusVo queryPowerStatus(Equipment equipment) {
        if (StringUtils.isAnyBlank(equipment.getPowerIp())
                || Objects.isNull(equipment.getPowerPort())) {
            return null;
        }
        PowerReqVo powerReqVo = new PowerReqVo();
        powerReqVo.setIp(equipment.getPowerIp());
        powerReqVo.setPort(equipment.getPowerPort());
        powerReqVo.setType(PowerCmdEnum.CMD_QUERY.getType());
        return powerStatusService.queryPowerStatus(powerReqVo);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }
}