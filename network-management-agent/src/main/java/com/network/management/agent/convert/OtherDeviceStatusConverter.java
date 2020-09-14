package com.network.management.agent.convert;

import com.network.management.domain.bo.DataBo;
import com.network.management.domain.bo.OtherDeviceStatusBo;
import com.network.management.common.convert.Converter;
import com.network.management.domain.dao.OtherDeviceStatus;
import com.network.management.domain.enums.YnEnum;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * OtherDeviceStatus对象转换类
 * @author yyc
 * @date 2020/9/13 23:07
 */
@Component
public class OtherDeviceStatusConverter implements Converter<DataBo<?>, OtherDeviceStatus> {
    @Override
    public OtherDeviceStatus convert(DataBo<?> dataBo) {
        if(Objects.nonNull(dataBo)){
            OtherDeviceStatus otherDeviceStatus = new OtherDeviceStatus();
            otherDeviceStatus.setIp(dataBo.getIp());
            otherDeviceStatus.setType(dataBo.getType());
            otherDeviceStatus.setYn(YnEnum.YES.getCode());
            Object obj = dataBo.getDataObj();
            if(obj instanceof OtherDeviceStatusBo){
                OtherDeviceStatusBo statusBo = (OtherDeviceStatusBo) obj;
                otherDeviceStatus.setStatus(statusBo.getStatus());
            }
            return otherDeviceStatus;
        }
        return null;
    }

    @Override
    public DataBo<?> reverseConvert(OtherDeviceStatus s) {
        return null;
    }
}
