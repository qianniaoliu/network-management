package com.network.management.service.impl;

import com.network.management.User;
import com.network.management.vo.RegistryVo;
import com.network.management.enums.YnEnum;
import com.network.management.common.exception.IllegalParamException;
import com.network.management.mapper.UserMapper;
import com.network.management.service.UserService;
import com.network.management.service.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Objects;

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
        if(Objects.nonNull(user)){
            throw new IllegalParamException("用户名已存在!");
        }
        user = new UserConverter().convert(registryVo);
        user.setYn(YnEnum.YES.getCode());
        user.setCreated(new Date());
        user.setModified(new Date());
        userMapper.insert(user);
    }
}
