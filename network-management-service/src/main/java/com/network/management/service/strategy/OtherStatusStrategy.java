package com.network.management.service.strategy;

import com.google.common.collect.Maps;
import com.network.management.domain.bo.DeviceStatusResultBo;
import com.network.management.domain.dao.Equipment;
import com.network.management.domain.dao.OtherDeviceStatus;
import com.network.management.domain.enums.DeviceTypeEnum;
import com.network.management.domain.search.EquipmentStatusSearch;
import com.network.management.domain.vo.DeviceStatusVo;
import com.network.management.domain.vo.OtherDeviceStatusVo;
import com.network.management.mapper.EquipmentMapper;
import com.network.management.mapper.OtherDeviceStatusMapper;
import com.network.management.service.DeviceStatusStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yusheng
 */
@Component
public class OtherStatusStrategy implements DeviceStatusStrategy {

    private final OtherDeviceStatusMapper otherDeviceStatusMapper;

    private final EquipmentMapper equipmentMapper;

    public OtherStatusStrategy(OtherDeviceStatusMapper otherDeviceStatusMapper, EquipmentMapper equipmentMapper) {
        this.otherDeviceStatusMapper = otherDeviceStatusMapper;
        this.equipmentMapper = equipmentMapper;
    }

    @Override
    public DeviceStatusResultBo search(EquipmentStatusSearch search) {
        DeviceStatusResultBo result = new DeviceStatusResultBo();
        List<OtherDeviceStatus> otherDeviceStatusList = otherDeviceStatusMapper.getByConditions(search);
        if (CollectionUtils.isEmpty(otherDeviceStatusList)) {
            return result;
        }
        Map<String, Equipment> equipmentMap = Maps.newHashMap();
        List<DeviceStatusVo> deviceStatusVoList = otherDeviceStatusList.stream()
                .map(otherDeviceStatus -> {
                    DeviceStatusVo<OtherDeviceStatusVo> deviceStatusVo = new DeviceStatusVo<>();
                    OtherDeviceStatusVo otherDeviceStatusVo = new OtherDeviceStatusVo(otherDeviceStatus.getStatus());
                    otherDeviceStatusVo.setCreated(otherDeviceStatus.getCreated());
                    deviceStatusVo.setStatusObj(otherDeviceStatusVo);
                    equipmentMap.computeIfAbsent(otherDeviceStatus.getIp(), ip -> equipmentMapper.getByIp(ip));
                    deviceStatusVo.fillEquipment(equipmentMap.get(otherDeviceStatus.getIp()));
                    return deviceStatusVo;
                }).collect(Collectors.toList());
        result.setData(deviceStatusVoList);
        result.setCount(otherDeviceStatusMapper.count(search));
        return result;
    }

    @Override
    public Boolean support(Integer equipmentType) {
        if(DeviceTypeEnum.OTHER_STATION.getType().equals(equipmentType)){
            return true;
        }
        return false;
    }
}
