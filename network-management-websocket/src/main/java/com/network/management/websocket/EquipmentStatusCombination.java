/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.network.management.websocket;

import com.network.management.domain.vo.DeviceStatusVo;
import com.network.management.domain.vo.PowerStatusVo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * 设备状态和电源状态组合对象
 *
 * @author shenlong
 * @version EquipmentStatusCombination.java, v 0.1 2022年09月03日 5:23 PM shenlong
 */
@Data
@AllArgsConstructor
public class EquipmentStatusCombination {

    /**
     * 设备id
     */
    private Integer equipmentId;

    /**
     * 设备状态
     */
    private DeviceStatusVo<?> deviceStatusVo;

    /**
     * 电源状态
     */
    private PowerStatusVo powerStatusVo;

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        EquipmentStatusCombination that = (EquipmentStatusCombination) o;
        return Objects.equals(equipmentId, that.equipmentId) && Objects.equals(deviceStatusVo, that.deviceStatusVo)
                && Objects.equals(powerStatusVo, that.powerStatusVo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equipmentId, deviceStatusVo, powerStatusVo);
    }
}