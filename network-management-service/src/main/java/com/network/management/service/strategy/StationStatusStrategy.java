package com.network.management.service.strategy;

import com.google.common.collect.Maps;
import com.network.management.domain.bo.DeviceStatusResultBo;
import com.network.management.domain.dao.Equipment;
import com.network.management.domain.dao.StationStatus;
import com.network.management.domain.enums.DeviceTypeEnum;
import com.network.management.domain.search.EquipmentStatusSearch;
import com.network.management.domain.vo.DeviceStatusVo;
import com.network.management.domain.vo.FlashStationStatusVo;
import com.network.management.domain.vo.WebStationStatusVo;
import com.network.management.mapper.EquipmentMapper;
import com.network.management.mapper.StationStatusMapper;
import com.network.management.service.DeviceStatusStrategy;
import com.network.management.service.converter.FlashStationStatusVoConverter;
import com.network.management.service.converter.WebStationStatusVoConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yusheng
 */
@Component
public class StationStatusStrategy implements DeviceStatusStrategy {

    private final StationStatusMapper stationStatusMapper;

    private final EquipmentMapper equipmentMapper;

    public StationStatusStrategy(StationStatusMapper stationStatusMapper, EquipmentMapper equipmentMapper) {
        this.stationStatusMapper = stationStatusMapper;
        this.equipmentMapper = equipmentMapper;
    }

    @Override
    public DeviceStatusResultBo search(EquipmentStatusSearch search) {
        DeviceStatusResultBo result = new DeviceStatusResultBo();
        List<StationStatus> stationStatusList = stationStatusMapper.getByConditions(search);
        if (CollectionUtils.isEmpty(stationStatusList)) {
            return result;
        }
        Map<String, Equipment> equipmentMap = Maps.newHashMap();
        List<DeviceStatusVo> deviceStatusVoList = stationStatusList.stream()
                .map(stationStatus -> {
                    DeviceStatusVo<Object> deviceStatusVo = new DeviceStatusVo();
                    if(search.getEquipmentType().equals(DeviceTypeEnum.WEB_STATION.getType())){
                        deviceStatusVo.setStatusObj(new WebStationStatusVoConverter().convert(stationStatus));
                    }else {
                        deviceStatusVo.setStatusObj(new FlashStationStatusVoConverter().convert(stationStatus));
                    }
                    equipmentMap.computeIfAbsent(stationStatus.getIp(), ip -> equipmentMapper.getByIp(ip));
                    deviceStatusVo.fillEquipment(equipmentMap.get(stationStatus.getIp()));
                    return deviceStatusVo;
                }).collect(Collectors.toList());
        result.setData(deviceStatusVoList);
        result.setCount(stationStatusMapper.count(search));
        return result;
    }

    @Override
    public Boolean support(Integer equipmentType) {
        if (DeviceTypeEnum.WEB_STATION.getType().equals(equipmentType)
                || DeviceTypeEnum.FLASH_STATION.getType().equals(equipmentType)) {
            return true;
        }
        return false;
    }
}
