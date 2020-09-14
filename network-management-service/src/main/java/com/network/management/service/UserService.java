package com.network.management.service;

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

}
