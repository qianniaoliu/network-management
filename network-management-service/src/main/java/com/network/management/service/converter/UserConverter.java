package com.network.management.service.converter;

import com.network.management.domain.dao.User;
import com.network.management.common.convert.Converter;
import com.network.management.domain.vo.RegistryVo;

/**
 * @author yusheng
 */
public class UserConverter implements Converter<RegistryVo, User> {
    @Override
    public User convert(RegistryVo source) {
        User user = new User();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setPassword(source.getPassword());
        user.setProfessionId(source.getProfessionId());
        user.setDepartmentId(source.getDepartmentId());
        return user;
    }

    @Override
    public RegistryVo reverseConvert(User s) {
        return null;
    }
}
