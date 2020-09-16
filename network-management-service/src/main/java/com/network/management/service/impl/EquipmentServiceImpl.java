package com.network.management.service.impl;

import com.network.management.agent.DeviceMonitorContext;
import com.network.management.agent.collector.Collector;
import com.network.management.common.exception.Assert;
import com.network.management.domain.bo.DataBo;
import com.network.management.domain.dao.Equipment;
import com.network.management.domain.enums.DeviceTypeEnum;
import com.network.management.domain.vo.DeviceStatusVo;
import com.network.management.mapper.EquipmentMapper;
import com.network.management.service.EquipmentService;
import com.network.management.service.converter.DeviceConverter;
import com.network.management.service.converter.DeviceStatusVoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yusheng
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentMapper equipmentMapper;

    public EquipmentServiceImpl(EquipmentMapper equipmentMapper) {
        this.equipmentMapper = equipmentMapper;
    }

    @Autowired
    private DeviceMonitorContext deviceMonitorContext;
    @Autowired
    private DeviceConverter deviceConverter;
    @Autowired
    private DeviceStatusVoConverter deviceStatusVoConverter;

    @Override
    public Integer add(Equipment equipment) {
        Assert.notNull(equipment, "equipment对象不能为空");
        equipment.initCreateInfo();
        return equipmentMapper.insert(equipment);
    }

    @Override
    public void update(Equipment equipment) {
        Assert.notNull(equipment, "equipment对象不能为空");
        equipment.initModifyInfo();
        equipmentMapper.updateByPrimaryKeySelective(equipment);
    }

    @Override
    public void delete(Set<Integer> ids) {
        Assert.notNull(ids, "ids对象不能为空");
        ids.forEach(equipmentMapper::deleteByPrimaryKey);
    }

    @Override
    public Equipment get(Integer id) {
        Assert.notNull(id, "id对象不能为空");
        return equipmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Equipment> getByBordId(Integer bordId) {
        List<Equipment> equipments = equipmentMapper.getByBordId(bordId);
        return Optional.ofNullable(equipments).orElse(Collections.emptyList());
    }

    @Override
    public DeviceStatusVo<?> queryStatus(Integer id) {
        Assert.notNull(id, "设备ip不能为空");
        Equipment equipment = get(id);
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
