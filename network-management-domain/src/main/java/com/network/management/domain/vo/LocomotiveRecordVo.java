package com.network.management.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.network.management.common.constants.CommonConstants;
import com.network.management.common.exception.IllegalParamException;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 机车记录请求对象
 *
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
@Data
public class LocomotiveRecordVo {

    /**
     * 车辆类型
     */
    private String vehicleType;

    /**
     * 进出方向
     */
    private String direction;

    /**
     * 节数
     */
    private Integer sectionNumber;

    /**
     * 位置（南翼/北翼）
     */
    private String location;

    /**
     * 发生时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date occurDate;

    public void checkParam() {
        if (StringUtils.isAnyBlank(vehicleType, direction, location)
                || ObjectUtils.isEmpty(sectionNumber)
                || ObjectUtils.isEmpty(occurDate)) {
            throw new IllegalParamException("缺少必填参数");
        }
        if (!StringUtils.equals(direction, CommonConstants.ENTRANCE_KEY)
                && !StringUtils.equals(direction, CommonConstants.OUT_KEY)) {
            throw new IllegalParamException("进出方向必须是'入场/出场'");
        }
        if (!StringUtils.equals(location, CommonConstants.SOUTH_LOCOMOTIVE_KEY)
                && !StringUtils.equals(location, CommonConstants.NORTH_LOCOMOTIVE_KEY)) {
            throw new IllegalParamException("位置必须是'南翼/北翼'");
        }
    }

}
