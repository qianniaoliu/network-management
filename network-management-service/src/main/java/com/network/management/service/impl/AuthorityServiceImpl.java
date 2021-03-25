package com.network.management.service.impl;

import com.network.management.domain.dao.Authority;
import com.network.management.domain.vo.AuthorityVo;
import com.network.management.mapper.AuthorityMapper;
import com.network.management.service.AuthorityService;
import com.network.management.service.converter.AuthorityVoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 授权明细服务实现类
 *
 * @author yyc
 * @date 2021/3/25 15:15
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {
    @Resource
    private AuthorityMapper authorityMapper;
    @Autowired
    private AuthorityVoConverter authorityVoConverter;

    @Override
    public void add(AuthorityVo authorityVo) {
        Authority authority = authorityVoConverter.reverseConvert(authorityVo);
        if (Objects.nonNull(authority)) {
            authorityMapper.insert(authority);
        }
    }

    @Override
    public void update(AuthorityVo authorityVo) {
        Authority authority = authorityVoConverter.reverseConvert(authorityVo);
        if (Objects.nonNull(authority)) {
            authorityMapper.updateByPrimaryKeySelective(authority);
        }
    }

    @Override
    public void delete(Integer id) {
        authorityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<AuthorityVo> queryAllAuthorityVos() {
        return authorityVoConverter.convertToList(authorityMapper.queryAllAuthorities());
    }

    @Override
    public AuthorityVo queryById(Integer id) {
        return authorityVoConverter.convert(authorityMapper.selectByPrimaryKey(id));
    }
}
