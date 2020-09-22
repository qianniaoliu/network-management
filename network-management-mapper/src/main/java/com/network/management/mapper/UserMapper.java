package com.network.management.mapper;

import com.network.management.domain.UserSearch;
import com.network.management.domain.dao.User;

import java.util.List;


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
     * @return 主键id
     */
    Integer insert(User user);

    /**
     * 修改用户数据
     * @param user 用户信息
     */
    void updateByPrimaryKeySelective(User user);

    /**
     * 通过主键id删除用户数据
     * @param id 主键id
     */
    void deleteByPrimaryKey(Integer id);

    /**
     * 条件查询总数
     * @param search 搜索条件
     * @return 总数
     */
    Integer count(UserSearch search);

    /**
     * 条件搜索
     * @param search 搜索条件
     * @return 用户列表
     */
    List<User> search(UserSearch search);

    /**
     * 根据用户id获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    User selectByPrimaryKey(Integer userId);
}
