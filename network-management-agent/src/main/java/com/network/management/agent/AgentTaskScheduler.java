package com.network.management.agent;

import com.network.management.agent.Monitor.Monitor;
import com.network.management.agent.convert.DeviceBoConverter;
import com.network.management.common.threadpool.ThreadPoolUtils;
import com.network.management.domain.bo.DeviceBo;
import com.network.management.domain.dao.Equipment;
import com.network.management.domain.enums.DeviceTypeEnum;
import com.network.management.domain.enums.StatusTableEnum;
import com.network.management.mapper.EquipmentMapper;
import com.network.management.mapper.OtherDeviceStatusMapper;
import com.network.management.mapper.StationStatusMapper;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AgentTaskScheduler {
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private DeviceBoConverter converter;
    @Autowired
    private DeviceMonitorContext context;
    @Autowired
    private OtherDeviceStatusMapper otherDeviceStatusMapper;
    @Autowired
    private StationStatusMapper stationStatusMapper;

    @Scheduled(cron = "${collect.interval.time}")
    public void collectTimer() {
        List<Equipment> equipments = equipmentMapper.getAll();
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

    @Scheduled(cron = "${delete.interval.time}")
    public void deleteBefore30Days(){
        for(StatusTableEnum statusTableEnum : StatusTableEnum.values()){
            ThreadPoolUtils.getExecutorService().execute(() -> {
                try{
                    if(Objects.equals(StatusTableEnum.STATION_STATUS_TABLE.getTableName(), statusTableEnum.getTableName())){
                        stationStatusMapper.deleteBefore30Days();
                    }else if(Objects.equals(StatusTableEnum.OTHER_DEVICE_STATUS_TABLE.getTableName(), statusTableEnum.getTableName())){
                        otherDeviceStatusMapper.deleteBefore30Days();
                    }
                }catch (Exception e){
                    log.error("定时删除状态数据失败.", e);
                }
            });
        }
    }
}
