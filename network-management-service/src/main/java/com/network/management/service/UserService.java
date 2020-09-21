package com.network.management.service;

import com.network.management.domain.Page;
import com.network.management.domain.UserSearch;
import com.network.management.domain.dao.User;
import com.network.management.domain.vo.RegistryVo;

/**
 * 用户信息服务
 *
 * @author yusheng
 */
public interface UserService {

    /**
     * 注册用户信息
     * @param registryVo 注册对象
     */
    void add(RegistryVo registryVo);

    /**
     * 通过用户名获取用户信息
     * @param name 用户名
     * @return 用户信息
     */
    User queryByName(String name);

    /**
     * 修改用户信息
     * @param registryVo 修改信息
     */
    void update(RegistryVo registryVo);

    /**
     * 删除用户信息
     * @param id 用户id
     */
    void delete(Integer id);

    /**
     * 分页查询用户信息
     * @param search 条件对象
     * @return 分页数据
     */
    Page<User> search(UserSearch search);

}
