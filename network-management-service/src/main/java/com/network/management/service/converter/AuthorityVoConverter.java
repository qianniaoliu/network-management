package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.dao.Authority;
import com.network.management.domain.enums.YnEnum;
import com.network.management.domain.vo.AuthorityVo;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * AuthorityVo对象转换类
 * @author yyc
 * @date 2021/3/25 15:21
 */
@Component
public class AuthorityVoConverter implements Converter<Authority, AuthorityVo> {
    @Override
    public AuthorityVo convert(Authority authority) {
        if(Objects.nonNull(authority)){
            AuthorityVo authorityVo = new AuthorityVo();
            authorityVo.setId(authority.getId());
            authorityVo.setDesc(authority.getDesc());
            authorityVo.setCreated(authority.getCreated());
            authorityVo.setModified(authority.getModified());
            return authorityVo;
        }
        return null;
    }

    @Override
    public Authority reverseConvert(AuthorityVo authorityVo) {
        if(Objects.nonNull(authorityVo)){
            Authority authority = new Authority();
            authority.setId(authorityVo.getId());
            authority.setYn(YnEnum.YES.getCode());
            authority.setDesc(authorityVo.getDesc());
            if(Objects.isNull(authorityVo.getId())){
                authority.setCreated(new Date());
            }
            authority.setModified(new Date());
            return authority;
        }
        return null;
    }
}
