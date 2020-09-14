package com.network.management.mapper;

import com.network.management.domain.dao.User;


/**
 * 用户mapper
 *
 * @author yusheng
 * @date 2020-09-07
 */
public interface UserMapper {

    /**
     * 通过用户名获取密码
     * @param username 用户名
     * @return 用户对象
     */
    User selectByUserName(String username);

    /**
     * 插入一条用户数据
     * @param user 用户对象
     */
    void insert(User user);
}
