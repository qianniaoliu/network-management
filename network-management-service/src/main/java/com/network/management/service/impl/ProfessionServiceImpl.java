package com.network.management.service.impl;

import com.network.management.common.exception.Assert;
import com.network.management.common.exception.IllegalParamException;
import com.network.management.domain.dao.Profession;
import com.network.management.domain.search.Page;
import com.network.management.domain.search.ProfessionSearch;
import com.network.management.domain.vo.ProfessionVo;
import com.network.management.mapper.ProfessionMapper;
import com.network.management.service.ProfessionService;
import com.network.management.service.converter.ProfessionVoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 职位服务实现类
 * @author yyc
 * @date 2021/2/28 16:29
 */
@Service
@Slf4j
public class ProfessionServiceImpl implements ProfessionService {
    @Autowired
    private ProfessionMapper professionMapper;
    @Autowired
    private ProfessionVoConverter professionVoConverter;
    @Override
    public void add(ProfessionVo professionVo) {
        Assert.notNull(professionVo, "职位对象不能为空!");
        Profession pro = professionMapper.selectByProfessionName(professionVo.getProfessionName());
        if(Objects.nonNull(pro)){
            throw new IllegalParamException("职位已存在!");
        }
        Profession profession = professionVoConverter.reverseConvert(professionVo);
        if(Objects.isNull(profession)){
            throw new IllegalParamException("ProfessionVoConverter转换后的职位为空!");
        }
        professionMapper.insert(profession);
    }

    @Override
    public void update(ProfessionVo professionVo) {
        Assert.notNull(professionVo, "职位对象不能为空!");
        Profession profession = professionVoConverter.reverseConvert(professionVo);
        if(Objects.isNull(profession)){
            throw new IllegalParamException("ProfessionVoConverter转换后的职位为空!");
        }
        professionMapper.updateByPrimaryKeySelective(profession);
    }

    @Override
    public void delete(Integer id) {
        Assert.notNull(id, "职位id不能为空!");
        professionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ProfessionVo get(Integer id) {
        Assert.notNull(id, "职位id不能为空!");
        return professionVoConverter.convert(professionMapper.selectByPrimaryKey(id));
    }

    @Override
    public Page<ProfessionVo> search(ProfessionSearch search) {
        Page<ProfessionVo> result = new Page<>();
        List<Profession> professions = professionMapper.search(search);
        result.setData(professionVoConverter.convertToList(professions));
        result.setCount(professionMapper.count(search));
        result.setCurrentPage(search.getCurrentPage());
        result.setPageSize(search.getPageSize());
        return result;
    }

    @Override
    public List<ProfessionVo> queryAllProfessions() {
        return professionVoConverter.convertToList(professionMapper.queryAllProfession());
    }
}
