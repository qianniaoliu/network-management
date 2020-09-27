package com.network.management.service.impl;

import com.network.management.common.exception.Assert;
import com.network.management.domain.search.Page;
import com.network.management.domain.search.UserSearch;
import com.network.management.domain.dao.User;
import com.network.management.domain.vo.RegistryVo;
import com.network.management.mapper.UserMapper;
import com.network.management.service.UserService;
import com.network.management.service.converter.UserConverter;
import org.springframework.stereotype.Service;

import java.util.List;

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
        User user = userMapper.selectByUserName(registryVo.getUsername());
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

    @Override
    public void update(RegistryVo registryVo) {
        Assert.notNull(registryVo, "注册对象不能为空!");
        User user = userMapper.selectByUserName(registryVo.getUsername());
        Assert.notNull(user, "用户名不存在!");
        user = new UserConverter().convert(registryVo);
        user.initModifyInfo();
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delete(Integer id) {
        Assert.notNull(id, "主键id不能为空!");
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Page<User> search(UserSearch search) {
        Page<User> result = new Page<>();
        List<User> users = userMapper.search(search);
        result.setData(users);
        result.setCount(userMapper.count(search));
        result.setPageSize(search.getPageSize());
        result.setCurrentPage(search.getCurrentPage());
        return result;
    }

    @Override
    public User get(Integer userId) {
        Assert.notNull(userId, "userId不能为空");
        return userMapper.selectByPrimaryKey(userId);
    }
}
