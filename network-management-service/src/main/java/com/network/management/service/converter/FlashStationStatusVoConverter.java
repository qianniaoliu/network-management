package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.bo.FlashStationStatusBo;
import com.network.management.domain.vo.FlashStationStatusVo;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * FlashStationStatusVo对象转换类
 * @author yyc
 * @date 2020/9/16 22:49
 */
@Component
public class FlashStationStatusVoConverter implements Converter<FlashStationStatusBo, FlashStationStatusVo> {
    @Override
    public FlashStationStatusVo convert(FlashStationStatusBo flashStationStatusBo) {
        if(Objects.nonNull(flashStationStatusBo)){
            FlashStationStatusVo statusVo = new FlashStationStatusVo();
            statusVo.setCellStatus(flashStationStatusBo.getCellStatus());
            statusVo.setIpSecStatus(flashStationStatusBo.getIpSecStatus());
            statusVo.setIpSecSwitch(flashStationStatusBo.getIpSecSwitch());
            statusVo.setS1Status(flashStationStatusBo.getS1Status());
            statusVo.setWanInternet(flashStationStatusBo.getWanInternet());
            statusVo.setWanStatus(flashStationStatusBo.getWanStatus());
            return statusVo;
        }
        return null;
    }

    @Override
    public FlashStationStatusBo reverseConvert(FlashStationStatusVo s) {
        return null;
    }
}
