package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.excel.WebStationStatusExcel;
import com.network.management.domain.vo.DeviceStatusVo;
import com.network.management.domain.vo.WebStationStatusVo;
import org.springframework.stereotype.Component;

/**
 * @author yusheng
 */
@Component
public class WebStationStatusExcelConverter implements Converter<DeviceStatusVo, WebStationStatusExcel> {
    @Override
    public WebStationStatusExcel convert(DeviceStatusVo deviceStatusVo) {
        WebStationStatusVo webStationStatusVo = (WebStationStatusVo) deviceStatusVo.getStatusObj();
        WebStationStatusExcel result = new WebStationStatusExcel();
        result.setIp(deviceStatusVo.getIp());
        result.setName(deviceStatusVo.getName());
        result.setPosition(deviceStatusVo.getPosition());
        result.setApStatus(webStationStatusVo.getApStatus());
        result.setCellStatus(webStationStatusVo.getCellStatus());
        result.setNetManagerStatus(webStationStatusVo.getNetManagerStatus());
        result.setRfStatus(webStationStatusVo.getRfStatus());
        result.setSctpStatus(webStationStatusVo.getSctpStatus());
        result.setTimeClockStatus(webStationStatusVo.getTimeClockStatus());
        result.setWanStatus(webStationStatusVo.getWanStatus());
        result.setCreated(webStationStatusVo.getCreated());
        return result;
    }

    @Override
    public DeviceStatusVo reverseConvert(WebStationStatusExcel s) {
        return null;
    }
}
