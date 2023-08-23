package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.dao.LocomotiveRecord;
import com.network.management.domain.enums.YnEnum;
import com.network.management.domain.vo.LocomotiveRecordVo;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
@Component
public class LocomotiveRecordConverter implements Converter<LocomotiveRecordVo, LocomotiveRecord> {
    @Override
    public LocomotiveRecord convert(LocomotiveRecordVo recordRequest) {
        LocomotiveRecord result = new LocomotiveRecord();
        result.setVehicleType(recordRequest.getVehicleType());
        result.setDirection(recordRequest.getDirection());
        result.setSectionNumber(recordRequest.getSectionNumber());
        result.setLocation(recordRequest.getLocation());
        result.setOccurDate(recordRequest.getOccurDate());
        result.setYn(YnEnum.YES.getCode());
        Date date = new Date();
        result.setCreated(date);
        result.setModified(date);
        return result;
    }

    @Override
    public LocomotiveRecordVo reverseConvert(LocomotiveRecord source) {
        LocomotiveRecordVo result = new LocomotiveRecordVo();
        result.setVehicleType(source.getVehicleType());
        result.setDirection(source.getDirection());
        result.setSectionNumber(source.getSectionNumber());
        result.setLocation(source.getLocation());
        result.setOccurDate(source.getOccurDate());
        return result;
    }
}
