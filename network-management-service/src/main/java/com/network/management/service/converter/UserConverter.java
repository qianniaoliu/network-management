package com.network.management.service.converter;

import com.network.management.User;
import com.network.management.account.RegistryVo;
import org.springframework.core.convert.converter.Converter;

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
}
