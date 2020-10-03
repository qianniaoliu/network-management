package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.enums.ConnectionEnum;
import com.network.management.domain.enums.CreationEnum;
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
        result.setCellStatus(CreationEnum.getByKey(flashStationStatusVo.getCellStatus()).getDesc());
        result.setS1Status(ConnectionEnum.getByKey(flashStationStatusVo.getS1Status()).getDesc());
        result.setWanInternet(ConnectionEnum.getByKey(flashStationStatusVo.getWanInternet()).getDesc());
        result.setWanStatus(ConnectionEnum.getByKey(flashStationStatusVo.getWanStatus()).getDesc());
        result.setCreated(flashStationStatusVo.getCreated());
        return result;
    }

    @Override
    public DeviceStatusVo reverseConvert(FlashStationStatusExcel s) {
        return null;
    }

}
