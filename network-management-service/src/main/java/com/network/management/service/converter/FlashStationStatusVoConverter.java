package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.bo.FlashStationStatusBo;
import com.network.management.domain.dao.StationStatus;
import com.network.management.domain.vo.FlashStationStatusVo;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * FlashStationStatusVo对象转换类
 * @author yyc
 * @date 2020/9/16 22:49
 */
@Component
public class FlashStationStatusVoConverter implements Converter<StationStatus, FlashStationStatusVo> {

    @Override
    public FlashStationStatusVo convert(StationStatus stationStatus) {
        if(Objects.nonNull(stationStatus)){
            FlashStationStatusVo statusVo = new FlashStationStatusVo();
            statusVo.setCellStatus(stationStatus.getCellStatus());
            statusVo.setIpSecStatus(stationStatus.getIpSpecStatus());
            statusVo.setIpSecSwitch(stationStatus.getIpSecSwitchStatus());
            statusVo.setS1Status(stationStatus.getS1Status());
            statusVo.setWanInternet(stationStatus.getWanInternet());
            statusVo.setWanStatus(stationStatus.getWanStatus());
            statusVo.setCreated(stationStatus.getCreated());
            return statusVo;
        }
        return null;
    }

    @Override
    public StationStatus reverseConvert(FlashStationStatusVo s) {
        return null;
    }
}
