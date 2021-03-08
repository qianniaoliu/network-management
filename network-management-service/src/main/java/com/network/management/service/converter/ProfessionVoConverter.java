package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.dao.Profession;
import com.network.management.domain.enums.YnEnum;
import com.network.management.domain.vo.ProfessionVo;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * ProfessionVo对象转换类
 * @author yyc
 * @date 2021/3/1 15:20
 */
@Component
public class ProfessionVoConverter implements Converter<Profession, ProfessionVo> {
    @Override
    public ProfessionVo convert(Profession profession) {
        if(Objects.nonNull(profession)){
            ProfessionVo professionVo = new ProfessionVo();
            professionVo.setId(profession.getId());
            professionVo.setLevel(profession.getLevel());
            professionVo.setPriority(profession.getPriority());
            professionVo.setDescription(profession.getDescription());
            professionVo.setProfessionName(profession.getProfessionName());
            professionVo.setCreated(profession.getCreated());
            professionVo.setModified(profession.getModified());
            return professionVo;
        }
        return null;
    }

    @Override
    public Profession reverseConvert(ProfessionVo professionVo) {
        if(Objects.nonNull(professionVo)){
            Profession profession = new Profession();
            profession.setId(professionVo.getId());
            profession.setLevel(professionVo.getLevel());
            profession.setPriority(professionVo.getPriority());
            profession.setProfessionName(professionVo.getProfessionName());
            profession.setDescription(professionVo.getDescription());
            profession.setModified(new Date());
            if(Objects.isNull(professionVo.getId())){
                profession.setCreated(new Date());
                profession.setYn(YnEnum.YES.getCode());
            }
            return profession;
        }
        return null;
    }
}
