package com.network.management.agent.convert;

import com.network.management.Equipment;
import com.network.management.bo.DeviceBo;
import com.network.management.common.convert.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 设备基本信息装换类
 * @author yyc
 * @date 2020/9/13 13:58
 */
@Component
public class DeviceBoConverter implements Converter<Equipment, DeviceBo> {
    @Override
    public DeviceBo convert(Equipment equipment) {
        if(Objects.nonNull(equipment)){
            DeviceBo deviceBo = new DeviceBo();
            deviceBo.setId(equipment.getId());
            deviceBo.setEquipmentType(equipment.getEquipmentType());
            deviceBo.setIp(equipment.getIp());
            deviceBo.setName(equipment.getName());
            deviceBo.setPassword(equipment.getPassword());
            deviceBo.setUsername(equipment.getUsername());
            return deviceBo;
        }
        return null;
    }

    @Override
    public Equipment reverseConvert(DeviceBo s) {
        return null;
    }
}
