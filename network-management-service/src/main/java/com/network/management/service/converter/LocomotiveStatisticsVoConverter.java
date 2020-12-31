package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.bo.LocomotiveNumberBo;
import com.network.management.domain.vo.LocomotiveStatisticsVo;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * LocomotiveStatisticsVo转换类
 * @author yyc
 * @date 2020/12/31 19:16
 */
@Component
public class LocomotiveStatisticsVoConverter implements Converter<LocomotiveNumberBo, LocomotiveStatisticsVo> {
    @Override
    public LocomotiveStatisticsVo convert(LocomotiveNumberBo locomotiveNumberBo) {
        if(Objects.nonNull(locomotiveNumberBo)){
            LocomotiveStatisticsVo locomotiveStatisticsVo = new LocomotiveStatisticsVo();
            locomotiveStatisticsVo.setTotalNumber(locomotiveNumberBo.getTotal());
            locomotiveStatisticsVo.setYesterdayNumber(locomotiveNumberBo.getYesterday());
            locomotiveStatisticsVo.setTodayNumber(locomotiveNumberBo.getToday());
            return locomotiveStatisticsVo;
        }
        return null;
    }

    @Override
    public LocomotiveNumberBo reverseConvert(LocomotiveStatisticsVo s) {
        return null;
    }
}
