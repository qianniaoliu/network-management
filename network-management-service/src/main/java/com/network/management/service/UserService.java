package com.network.management.service;

import com.network.management.account.RegistryVo;

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

}
