package com.network.management.mapper;

import com.network.management.User;


/**
 * @author yusheng
 * @date 2020-09-07
 */
public interface UserMapper {

    /**
     * 通过用户名获取密码
     * @param username
     * @return
     */
    User selectByUserName(String username);
}
