package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.excel.FlashStationStatusExcel;
import com.network.management.domain.excel.OtherDeviceStatusExcel;
import com.network.management.domain.vo.DeviceStatusVo;
import com.network.management.domain.vo.OtherDeviceStatusVo;
import org.springframework.stereotype.Component;

/**
 * @author yusheng
 */
@Component
public class OtherDeviceStatusExcelConverter implements Converter<DeviceStatusVo, OtherDeviceStatusExcel> {
    @Override
    public OtherDeviceStatusExcel convert(DeviceStatusVo deviceStatusVo) {
        OtherDeviceStatusVo otherDeviceStatusVo = (OtherDeviceStatusVo) deviceStatusVo.getStatusObj();
        OtherDeviceStatusExcel result = new OtherDeviceStatusExcel();
        result.setIp(deviceStatusVo.getIp());
        result.setName(deviceStatusVo.getName());
        result.setPosition(deviceStatusVo.getPosition());
        result.setStatus(otherDeviceStatusVo.getStatus());
        return result;
    }

    @Override
    public DeviceStatusVo reverseConvert(OtherDeviceStatusExcel s) {
        return null;
    }
}
