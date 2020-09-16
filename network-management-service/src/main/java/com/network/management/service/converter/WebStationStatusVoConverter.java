package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.bo.WebStationStatusBo;
import com.network.management.domain.vo.WebStationStatusVo;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * WebStationStatusVo对象转换类
 * @author yyc
 * @date 2020/9/16 22:59
 */
@Component
public class WebStationStatusVoConverter implements Converter<WebStationStatusBo, WebStationStatusVo> {
    @Override
    public WebStationStatusVo convert(WebStationStatusBo webStationStatusBo) {
        if(Objects.nonNull(webStationStatusBo)){
            WebStationStatusVo webStationStatusVo = new WebStationStatusVo();
            webStationStatusVo.setApStatus(webStationStatusBo.getApStatus());
            webStationStatusVo.setCellStatus(webStationStatusBo.getCellStatus());
            webStationStatusVo.setIpSpecStatus(webStationStatusBo.getIpSpecStatus());
            webStationStatusVo.setNetManagerStatus(webStationStatusBo.getNetManagerStatus());
            webStationStatusVo.setRfStatus(webStationStatusBo.getRfStatus());
            webStationStatusVo.setSctpStatus(webStationStatusBo.getSctpStatus());
            webStationStatusVo.setTimeClockStatus(webStationStatusBo.getTimeClockStatus());
            webStationStatusVo.setUcStatus(webStationStatusBo.getUcStatus());
            webStationStatusVo.setWanStatus(webStationStatusBo.getWanStatus());
            return webStationStatusVo;
        }
        return null;
    }

    @Override
    public WebStationStatusBo reverseConvert(WebStationStatusVo s) {
        return null;
    }
}
