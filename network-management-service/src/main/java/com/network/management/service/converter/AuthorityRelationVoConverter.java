package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.dao.AuthorityRelation;
import com.network.management.domain.vo.AuthorityRelationVo;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * AuthorityRelationVo对象装换类
 * @author yyc
 * @date 2021/3/25 15:29
 */
@Component
public class AuthorityRelationVoConverter implements Converter<AuthorityRelation, AuthorityRelationVo> {
    @Override
    public AuthorityRelationVo convert(AuthorityRelation authorityRelation) {
        if(Objects.nonNull(authorityRelation)){
            AuthorityRelationVo authorityRelationVo = new AuthorityRelationVo();
            authorityRelationVo.setAuthorityId(authorityRelation.getAuthorityId());
            authorityRelationVo.setId(authorityRelation.getId());
            authorityRelationVo.setUserId(authorityRelation.getUserId());
            authorityRelationVo.setCreated(authorityRelation.getCreated());
            authorityRelationVo.setModified(authorityRelation.getModified());
            return authorityRelationVo;
        }
        return null;
    }

    @Override
    public AuthorityRelation reverseConvert(AuthorityRelationVo authorityRelationVo) {
        if(Objects.nonNull(authorityRelationVo)){
            AuthorityRelation authorityRelation = new AuthorityRelation();
            authorityRelation.setAuthorityId(authorityRelationVo.getAuthorityId());
            authorityRelation.setModified(new Date());
            authorityRelation.setUserId(authorityRelationVo.getUserId());
            if(Objects.nonNull(authorityRelationVo.getId())){
                authorityRelation.setCreated(new Date());
            }
            return authorityRelation;
        }
        return null;
    }
}
