package com.network.management.agent.convert;

import com.network.management.common.CommonUtils;
import com.network.management.common.convert.Converter;
import com.network.management.domain.bo.DataBo;
import com.network.management.domain.bo.FlashStationStatusBo;
import com.network.management.domain.bo.WebStationStatusBo;
import com.network.management.domain.dao.StationStatus;
import com.network.management.domain.enums.FlashStationStatusEnum;
import com.network.management.domain.enums.WebStationStatusEnum;
import com.network.management.domain.enums.YnEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 基准状态数据转换类
 * @author yyc
 * @date 2020/9/15 16:32
 */
@Component
public class StationStatusConverter implements Converter<DataBo<?>, StationStatus> {

    @Override
    public StationStatus convert(DataBo<?> dataBo) {
        if(Objects.nonNull(dataBo)){
            StationStatus stationStatus = new StationStatus();
            stationStatus.setIp(dataBo.getIp());
            stationStatus.setType(dataBo.getType());
            stationStatus.setYn(YnEnum.YES.getCode());
            fillDefaultStatus(stationStatus);
            Object obj = dataBo.getDataObj();
            if(obj instanceof FlashStationStatusBo){
                FlashStationStatusBo flashStationStatusBo = (FlashStationStatusBo)obj;
                fillFlashStationStatus(stationStatus, flashStationStatusBo);
            }else if(obj instanceof WebStationStatusBo){
                WebStationStatusBo webStationStatusBo = (WebStationStatusBo)obj;
                fillWebStationStatus(stationStatus, webStationStatusBo);
            }
            return stationStatus;
        }
        return null;
    }

    /**
     * 填充web界面基站状态数据
     * @param stationStatus {@link StationStatus}
     * @param webStationStatusBo {@link WebStationStatusBo}
     */
    private void fillWebStationStatus(StationStatus stationStatus, WebStationStatusBo webStationStatusBo){
        Integer timeClockStatus = CommonUtils.String2Integer(webStationStatusBo.getTimeClockStatus());
        stationStatus.setTimeClockStatus(Objects.isNull(timeClockStatus) ? YnEnum.NO.getCode() : timeClockStatus);
        stationStatus.setRfStatus(Objects.equals(WebStationStatusEnum.WEB_STATUS_1.getStatus(), webStationStatusBo.getRfStatus()) ? YnEnum.YES.getCode() : YnEnum.NO.getCode());
        stationStatus.setApStatus(Objects.equals(WebStationStatusEnum.WEB_STATUS_0.getStatus(), webStationStatusBo.getApStatus()) ? YnEnum.YES.getCode() : YnEnum.NO.getCode());
        stationStatus.setWanStatus(WebStationStatusEnum.WEB_STATUS_UP.getStatus().equalsIgnoreCase(webStationStatusBo.getWanStatus()) ? YnEnum.YES.getCode() : YnEnum.NO.getCode());
        stationStatus.setIpSpecStatus(WebStationStatusEnum.WEB_STATUS_ENABLED.getStatus().equalsIgnoreCase(webStationStatusBo.getIpSpecStatus()) ? YnEnum.YES.getCode() : YnEnum.NO.getCode());
        stationStatus.setSctpStatus(Objects.equals(WebStationStatusEnum.WEB_STATUS_0.getStatus(), webStationStatusBo.getSctpStatus()) ? YnEnum.YES.getCode() : YnEnum.NO.getCode());
        stationStatus.setNetManagerStatus(Objects.equals(WebStationStatusEnum.WEB_STATUS_1.getStatus(), webStationStatusBo.getNetManagerStatus()) ? YnEnum.YES.getCode() : YnEnum.NO.getCode());
        stationStatus.setCellStatus(Objects.equals(WebStationStatusEnum.WEB_STATUS_0.getStatus(), webStationStatusBo.getCellStatus()) ? YnEnum.YES.getCode() : YnEnum.NO.getCode());
        Integer ucStatus = CommonUtils.String2Integer(webStationStatusBo.getUcStatus());
        stationStatus.setUcStatus(Objects.isNull(ucStatus) ? YnEnum.NO.getCode() : ucStatus);
    }

    /**
     * 填充flash界面基站状态数据
     * @param stationStatus {@link StationStatus}
     * @param flashStationStatusBo {@link FlashStationStatusBo}
     */
    private void fillFlashStationStatus(StationStatus stationStatus, FlashStationStatusBo flashStationStatusBo){
        stationStatus.setWanStatus(StringUtils.isEmpty(flashStationStatusBo.getWanStatus()) ? YnEnum.NO.getCode() : YnEnum.YES.getCode());
        stationStatus.setWanInternet(Objects.equals(FlashStationStatusEnum.FLASH_STATUS_OK.getStatus(), flashStationStatusBo.getWanInternet()) ? YnEnum.YES.getCode() : YnEnum.NO.getCode());
        stationStatus.setIpSecSwitchStatus(StringUtils.isEmpty(flashStationStatusBo.getIpSecSwitch()) || Objects.equals(FlashStationStatusEnum.FLASH_STATUS_OFF.getStatus(), flashStationStatusBo.getIpSecSwitch())
                ? YnEnum.NO.getCode() : YnEnum.YES.getCode());
        stationStatus.setIpSpecStatus(StringUtils.isEmpty(flashStationStatusBo.getIpSecStatus()) || Objects.equals(FlashStationStatusEnum.FLASH_STATUS_FAIL.getStatus(), flashStationStatusBo.getIpSecStatus())
                ? YnEnum.NO.getCode() : YnEnum.YES.getCode());
        stationStatus.setS1Status(Objects.equals(FlashStationStatusEnum.FLASH_STATUS_OK.getStatus(), flashStationStatusBo.getS1Status()) ? YnEnum.YES.getCode() : YnEnum.NO.getCode());
        stationStatus.setCellStatus(Objects.equals(FlashStationStatusEnum.FLASH_STATUS_UP.getStatus(), flashStationStatusBo.getCellStatus()) ? YnEnum.YES.getCode() : YnEnum.NO.getCode());
    }

    /**
     * 填充默认状态(未连接状态)
     * @param stationStatus {@link StationStatus}
     */
    private void fillDefaultStatus(StationStatus stationStatus){
        stationStatus.setApStatus(YnEnum.NO.getCode());
        stationStatus.setCellStatus(YnEnum.NO.getCode());
        stationStatus.setIpSecSwitchStatus(YnEnum.NO.getCode());
        stationStatus.setIpSpecStatus(YnEnum.NO.getCode());
        stationStatus.setNetManagerStatus(YnEnum.NO.getCode());
        stationStatus.setRfStatus(YnEnum.NO.getCode());
        stationStatus.setS1Status(YnEnum.NO.getCode());
        stationStatus.setSctpStatus(YnEnum.NO.getCode());
        stationStatus.setTimeClockStatus(YnEnum.NO.getCode());
        stationStatus.setUcStatus(YnEnum.NO.getCode());
        stationStatus.setWanInternet(YnEnum.NO.getCode());
        stationStatus.setWanStatus(YnEnum.NO.getCode());
    }

    @Override
    public DataBo<?> reverseConvert(StationStatus s) {
        return null;
    }
}
