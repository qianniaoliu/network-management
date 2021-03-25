package com.network.management.service.impl;

import com.network.management.domain.dao.AuthorityRelation;
import com.network.management.domain.vo.AuthorityRelationVo;
import com.network.management.mapper.AuthorityRelationMapper;
import com.network.management.service.AuthorityRelationService;
import com.network.management.service.converter.AuthorityRelationVoConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 用户授权关系实现类
 *
 * @author yyc
 * @date 2021/3/25 15:36
 */
@Service
public class AuthorityRelationServiceImpl implements AuthorityRelationService {
    @Resource
    private AuthorityRelationMapper authorityRelationMapper;
    @Autowired
    private AuthorityRelationVoConverter authorityRelationVoConverter;

    @Override
    public void add(List<AuthorityRelationVo> authorityRelationVos) {
        List<AuthorityRelation> authorityRelations = authorityRelationVoConverter.reverseConvertToList(authorityRelationVos);
        ListUtils.emptyIfNull(authorityRelations)
                .stream()
                .filter(Objects::nonNull)
                .forEach(authorityRelation -> authorityRelationMapper.insert(authorityRelation));
    }

    @Override
    public void deleteByUserId(Integer userId) {
        authorityRelationMapper.deleteByUserId(userId);
    }

    @Override
    public void updateAuthorityRelationVos(List<AuthorityRelationVo> authorityRelationVos) {
        if (CollectionUtils.isNotEmpty(authorityRelationVos)) {
            Integer userId = authorityRelationVos.get(0).getUserId();
            if (Objects.nonNull(userId)) {
                deleteByUserId(userId);
                add(authorityRelationVos);
            }
        }
    }

    @Override
    public List<AuthorityRelationVo> queryAllAuthorityRelationVos(Integer userId) {
        return authorityRelationVoConverter.convertToList(authorityRelationMapper.queryAuthorityRelationByUserId(userId));
    }
}
