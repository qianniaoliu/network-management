package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.enums.ActivationEnum;
import com.network.management.domain.enums.ConnectionEnum;
import com.network.management.domain.enums.CreationEnum;
import com.network.management.domain.enums.TimepieceEnum;
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
        result.setApStatus(CreationEnum.getByKey(webStationStatusVo.getApStatus()).getDesc());
        result.setCellStatus(CreationEnum.getByKey(webStationStatusVo.getCellStatus()).getDesc());
        result.setNetManagerStatus(ConnectionEnum.getByKey(webStationStatusVo.getNetManagerStatus()).getDesc());
        result.setRfStatus(ActivationEnum.getByKey(webStationStatusVo.getRfStatus()).getDesc());
        result.setSctpStatus(ConnectionEnum.getByKey(webStationStatusVo.getSctpStatus()).getDesc());
        result.setTimeClockStatus(TimepieceEnum.getByKey(webStationStatusVo.getTimeClockStatus()).getDesc());
        result.setWanStatus(ConnectionEnum.getByKey(webStationStatusVo.getWanStatus()).getDesc());
        result.setCreated(webStationStatusVo.getCreated());
        return result;
    }

    @Override
    public DeviceStatusVo reverseConvert(WebStationStatusExcel s) {
        return null;
    }
}
