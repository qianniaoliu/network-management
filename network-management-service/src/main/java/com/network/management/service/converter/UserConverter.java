package com.network.management.service.converter;

import com.network.management.User;
import com.network.management.common.convert.Converter;
import com.network.management.vo.RegistryVo;

/**
 * @author yusheng
 */
public class UserConverter implements Converter<RegistryVo, User> {
    @Override
    public User convert(RegistryVo source) {
        User user = new User();
        user.setUsername(source.getUserName());
        user.setPassword(source.getPassword());
        return user;
    }

    @Override
    public RegistryVo reverseConvert(User s) {
        return null;
    }
}
