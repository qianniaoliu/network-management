package com.network.management.service.impl;

import com.network.management.common.exception.Assert;
import com.network.management.domain.dao.User;
import com.network.management.domain.vo.RegistryVo;
import com.network.management.mapper.UserMapper;
import com.network.management.service.UserService;
import com.network.management.service.converter.UserConverter;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * @author yusheng
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void add(RegistryVo registryVo) {
        Assert.notNull(registryVo, "注册对象不能为空!");
        User user = userMapper.selectByUserName(registryVo.getUserName());
        Assert.isNull(user, "用户名已存在!");
        user = new UserConverter().convert(registryVo);
        user.initCreateInfo();
        userMapper.insert(user);
    }

    @Override
    public User queryByName(String name) {
        Assert.notNull(name, "用户名不能为空!");
        return userMapper.selectByUserName(name);
    }
}
