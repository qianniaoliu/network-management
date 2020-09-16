package com.network.management.service.converter;

import com.network.management.agent.convert.StationStatusConverter;
import com.network.management.common.convert.Converter;
import com.network.management.domain.bo.DataBo;
import com.network.management.domain.bo.FlashStationStatusBo;
import com.network.management.domain.bo.OtherDeviceStatusBo;
import com.network.management.domain.bo.WebStationStatusBo;
import com.network.management.domain.dao.StationStatus;
import com.network.management.domain.vo.DeviceStatusVo;
import com.network.management.domain.vo.FlashStationStatusVo;
import com.network.management.domain.vo.OtherDeviceStatusVo;
import com.network.management.domain.vo.WebStationStatusVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * DeviceStatusVo对象转换类
 * @author yyc
 * @date 2020/9/16 22:42
 */
@Component
public class DeviceStatusVoConverter implements Converter<DataBo<?>, DeviceStatusVo<?>> {
    @Autowired
    private FlashStationStatusVoConverter flashStationStatusVoConverter;
    @Autowired
    private WebStationStatusVoConverter webStationStatusVoConverter;
    @Autowired
    private StationStatusConverter stationStatusConverter;
    @Override
    public DeviceStatusVo<?> convert(DataBo<?> dataBo) {
        if(Objects.nonNull(dataBo)){
            if(dataBo.getDataObj() instanceof FlashStationStatusBo){
                return getFlashDeviceStatusVo(dataBo);
            }else if(dataBo.getDataObj() instanceof WebStationStatusBo){
                return getWebDeviceStatusVo(dataBo);
            }else if(dataBo.getDataObj() instanceof OtherDeviceStatusBo){
                return getOtherDeviceStatusVo(dataBo);
            }
        }
        return null;
    }

    /**
     * 获取DeviceStatusVo对象
     * @param dataBo {@link DataBo}
     * @return {@link DeviceStatusVo<OtherDeviceStatusVo>}
     */
    private DeviceStatusVo<OtherDeviceStatusVo> getOtherDeviceStatusVo(DataBo<?> dataBo) {
        OtherDeviceStatusBo otherDeviceStatusBo = (OtherDeviceStatusBo)dataBo.getDataObj();
        DeviceStatusVo<OtherDeviceStatusVo> deviceStatusVo = new DeviceStatusVo<OtherDeviceStatusVo>();
        deviceStatusVo.setIp(dataBo.getIp());
        deviceStatusVo.setEquipmentType(dataBo.getType());
        deviceStatusVo.setStatusObj(new OtherDeviceStatusVo(otherDeviceStatusBo.getStatus()));
        return deviceStatusVo;
    }

    /**
     * 获取DeviceStatusVo对象
     * @param dataBo {@link DataBo}
     * @return {@link DeviceStatusVo<WebStationStatusVo>}
     */
    private DeviceStatusVo<WebStationStatusVo> getWebDeviceStatusVo(DataBo<?> dataBo) {
        DeviceStatusVo<WebStationStatusVo> deviceStatusVo = new DeviceStatusVo<WebStationStatusVo>();
        deviceStatusVo.setEquipmentType(dataBo.getType());
        deviceStatusVo.setIp(dataBo.getIp());
        StationStatus stationStatus = stationStatusConverter.convert(dataBo);
        deviceStatusVo.setStatusObj(webStationStatusVoConverter.convert(stationStatus));
        return deviceStatusVo;
    }

    /**
     * 获取DeviceStatusVo对象
     * @param dataBo {@link DataBo}
     * @return {@link DeviceStatusVo<FlashStationStatusVo>}
     */
    private DeviceStatusVo<FlashStationStatusVo> getFlashDeviceStatusVo(DataBo<?> dataBo) {
        DeviceStatusVo<FlashStationStatusVo> deviceStatusVo = new DeviceStatusVo<FlashStationStatusVo>();
        deviceStatusVo.setIp(dataBo.getIp());
        deviceStatusVo.setEquipmentType(dataBo.getType());
        StationStatus stationStatus = stationStatusConverter.convert(dataBo);
        deviceStatusVo.setStatusObj(flashStationStatusVoConverter.convert(stationStatus));
        return deviceStatusVo;
    }

    @Override
    public DataBo<?> reverseConvert(DeviceStatusVo<?> s) {
        return null;
    }
}
