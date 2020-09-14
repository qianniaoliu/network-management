package com.network.management.service.impl;

import com.network.management.domain.dao.User;
import com.network.management.domain.enums.YnEnum;
import com.network.management.common.exception.Assert;
import com.network.management.mapper.UserMapper;
import com.network.management.service.UserService;
import com.network.management.service.converter.UserConverter;
import com.network.management.domain.vo.RegistryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户服务实现类
 *
 * @author yusheng
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(RegistryVo registryVo) {
        Assert.notNull(registryVo, "注册对象不能为空!");
        User user = userMapper.selectByUserName(registryVo.getUserName());
        Assert.isNull(user, "用户名已存在!");
        user = new UserConverter().convert(registryVo);
        user.setYn(YnEnum.YES.getCode());
        user.setCreated(new Date());
        user.setModified(new Date());
        userMapper.insert(user);
    }
}
