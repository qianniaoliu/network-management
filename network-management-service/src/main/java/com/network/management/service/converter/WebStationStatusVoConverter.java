package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.dao.StationStatus;
import com.network.management.domain.vo.WebStationStatusVo;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * WebStationStatusVo对象转换类
 * @author yyc
 * @date 2020/9/16 22:59
 */
@Component
public class WebStationStatusVoConverter implements Converter<StationStatus, WebStationStatusVo> {
    @Override
    public WebStationStatusVo convert(StationStatus stationStatus) {
        if(Objects.nonNull(stationStatus)){
            WebStationStatusVo webStationStatusVo = new WebStationStatusVo();
            webStationStatusVo.setApStatus(stationStatus.getApStatus());
            webStationStatusVo.setCellStatus(stationStatus.getCellStatus());
            webStationStatusVo.setIpSecStatus(stationStatus.getIpSpecStatus());
            webStationStatusVo.setNetManagerStatus(stationStatus.getNetManagerStatus());
            webStationStatusVo.setRfStatus(stationStatus.getRfStatus());
            webStationStatusVo.setSctpStatus(stationStatus.getSctpStatus());
            webStationStatusVo.setTimeClockStatus(stationStatus.getTimeClockStatus());
            webStationStatusVo.setUcStatus(stationStatus.getUcStatus());
            webStationStatusVo.setWanStatus(stationStatus.getWanStatus());
            webStationStatusVo.setCreated(stationStatus.getCreated());
            return webStationStatusVo;
        }
        return null;
    }

    @Override
    public StationStatus reverseConvert(WebStationStatusVo s) {
        return null;
    }
}
