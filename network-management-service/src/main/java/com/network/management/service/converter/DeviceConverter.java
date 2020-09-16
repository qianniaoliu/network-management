package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.bo.DeviceBo;
import com.network.management.domain.dao.Equipment;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 设备信息转换类
 * @author yyc
 * @date 2020/9/16 22:20
 */
@Component
public class DeviceConverter implements Converter<Equipment, DeviceBo> {
    @Override
    public DeviceBo convert(Equipment equipment) {
        if(Objects.nonNull(equipment)){
            DeviceBo deviceBo = new DeviceBo();
            deviceBo.setId(equipment.getId());
            deviceBo.setIp(equipment.getIp());
            deviceBo.setEquipmentType(equipment.getEquipmentType());
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
