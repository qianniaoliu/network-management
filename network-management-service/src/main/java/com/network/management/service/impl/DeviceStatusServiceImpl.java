package com.network.management.service.impl;

import com.network.management.agent.DeviceMonitorContext;
import com.network.management.agent.collector.Collector;
import com.network.management.domain.bo.DataBo;
import com.network.management.domain.dao.Equipment;
import com.network.management.domain.enums.DeviceTypeEnum;
import com.network.management.domain.vo.DeviceStatusVo;
import com.network.management.service.DeviceStatusService;
import com.network.management.service.EquipmentService;
import com.network.management.service.converter.DeviceConverter;
import com.network.management.service.converter.DeviceStatusVoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * 设备状态查询服务实现类
 *
 * @author yyc
 * @date 2020/9/16 22:05
 */
@Component
@Slf4j
public class DeviceStatusServiceImpl implements DeviceStatusService {
    @Autowired
    private DeviceMonitorContext deviceMonitorContext;
    @Autowired
    private DeviceConverter deviceConverter;
    @Autowired
    private DeviceStatusVoConverter deviceStatusVoConverter;
    @Autowired
    private EquipmentService equipmentService;

    @Override
    public DeviceStatusVo<?> queryDeviceStatus(Integer id) {
        Assert.notNull(id, "设备ip不能为空");
        Equipment equipment = equipmentService.get(id);
        String deviceType = DeviceTypeEnum.getTypeKey(Objects.isNull(equipment) ? null : equipment.getEquipmentType());
        Collector collector = deviceMonitorContext.getCollector(deviceType);
        if (Objects.nonNull(collector)) {
            DataBo<?> dataBo = collector.collect(deviceConverter.convert(equipment));
            DeviceStatusVo<?> deviceStatusVo = deviceStatusVoConverter.convert(dataBo);
            fillDeviceStatusVo(deviceStatusVo, equipment);
            return deviceStatusVo;
        }
        return null;
    }

    /**
     * 填充DeviceStatusVo对象
     *
     * @param deviceStatusVo {@link DeviceStatusVo<?>}
     * @param equipment      {@link Equipment}
     */
    private void fillDeviceStatusVo(DeviceStatusVo<?> deviceStatusVo, Equipment equipment) {
        deviceStatusVo.setBordInformationId(equipment.getBordInformationId());
        deviceStatusVo.setIp(equipment.getIp());
        deviceStatusVo.setEquipmentType(equipment.getEquipmentType());
        deviceStatusVo.setInternalTime(equipment.getInternalTime());
        deviceStatusVo.setName(equipment.getName());
//        deviceStatusVo.setPassword(equipment.getPassword());
//        deviceStatusVo.setUsername(equipment.getUsername());
        deviceStatusVo.setX(equipment.getX());
        deviceStatusVo.setY(equipment.getY());
    }
}
