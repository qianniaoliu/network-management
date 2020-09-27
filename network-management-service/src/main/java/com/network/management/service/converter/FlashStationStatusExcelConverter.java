package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.excel.FlashStationStatusExcel;
import com.network.management.domain.vo.DeviceStatusVo;
import com.network.management.domain.vo.FlashStationStatusVo;
import org.springframework.stereotype.Component;

/**
 * @author yusheng
 */
@Component
public class FlashStationStatusExcelConverter implements Converter<DeviceStatusVo, FlashStationStatusExcel> {
    @Override
    public FlashStationStatusExcel convert(DeviceStatusVo deviceStatusVo) {
        FlashStationStatusVo flashStationStatusVo = (FlashStationStatusVo) deviceStatusVo.getStatusObj();
        FlashStationStatusExcel result = new FlashStationStatusExcel();
        result.setIp(deviceStatusVo.getIp());
        result.setName(deviceStatusVo.getName());
        result.setPosition(deviceStatusVo.getPosition());
        result.setCellStatus(flashStationStatusVo.getCellStatus());
        result.setIpSecStatus(flashStationStatusVo.getIpSecStatus());
        result.setIpSecSwitch(flashStationStatusVo.getIpSecSwitch());
        result.setS1Status(flashStationStatusVo.getS1Status());
        result.setWanInternet(flashStationStatusVo.getWanInternet());
        result.setWanStatus(flashStationStatusVo.getWanStatus());
        return result;
    }

    @Override
    public DeviceStatusVo reverseConvert(FlashStationStatusExcel s) {
        return null;
    }

}
