package com.network.management.agent;

import com.network.management.domain.dao.Equipment;
import com.network.management.agent.Monitor.Monitor;
import com.network.management.agent.convert.DeviceBoConverter;
import com.network.management.domain.bo.DeviceBo;
import com.network.management.common.threadpool.ThreadPoolUtils;
import com.network.management.domain.enums.DeviceTypeEnum;
import com.network.management.service.EquipmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 数据采集定时任务
 *
 * @author yyc
 * @date 2020/9/12 21:12
 */
@Component
public class AgentTaskScheduler {
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private DeviceBoConverter converter;
    @Autowired
    private DeviceMonitorContext context;

    @Scheduled(cron = "${collect.interval.time}")
    public void collectTimer() {
        List<Equipment> equipments = equipmentService.getAllEquipments();
        List<DeviceBo> deviceBos = converter.convertToList(equipments);
        if (!CollectionUtils.isEmpty(deviceBos)) {
            deviceBos.stream().filter(deviceBo -> Objects.nonNull(deviceBo)
                    && StringUtils.isNotEmpty(deviceBo.getIp())
                    && Objects.nonNull(deviceBo.getEquipmentType()))
                    .forEach(deviceBo -> {
                        ThreadPoolUtils.getExecutorService().execute(() -> {
                            String deviceType = DeviceTypeEnum.getTypeKey(deviceBo.getEquipmentType());
                            Monitor monitor = context.getMonitor(deviceType);
                            if (Objects.nonNull(monitor)) {
                                monitor.monitoring(deviceBo);
                            }
                        });
                    });
        }
    }
}
