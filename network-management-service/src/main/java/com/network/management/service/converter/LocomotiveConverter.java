package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.dao.Locomotive;
import com.network.management.domain.enums.YnEnum;
import com.network.management.domain.vo.LocomotiveVo;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 机车对象转换类
 * @author yyc
 * @date 2020/10/16 15:40
 */
@Component
public class LocomotiveConverter implements Converter<LocomotiveVo, Locomotive> {
    @Override
    public Locomotive convert(LocomotiveVo locomotiveVo) {
        if(Objects.nonNull(locomotiveVo)){
            Locomotive locomotive = new Locomotive();
            locomotive.setId(locomotiveVo.getId());
            locomotive.setDesc(locomotiveVo.getDesc());
            locomotive.setNum(locomotiveVo.getNum());
            locomotive.setUeIp(locomotiveVo.getUeIp());
            locomotive.setYn(YnEnum.YES.getCode());
            locomotive.setImsi(locomotiveVo.getImsi());
            return locomotive;
        }
        return null;
    }

    @Override
    public LocomotiveVo reverseConvert(Locomotive locomotive) {
        if(Objects.nonNull(locomotive)){
            LocomotiveVo locomotiveVo = new LocomotiveVo();
            locomotiveVo.setNum(locomotive.getNum());
            locomotiveVo.setId(locomotive.getId());
            locomotiveVo.setDesc(locomotive.getDesc());
            locomotiveVo.setUeIp(locomotive.getUeIp());
            locomotiveVo.setImsi(locomotive.getImsi());
            return locomotiveVo;
        }
        return null;
    }
}
