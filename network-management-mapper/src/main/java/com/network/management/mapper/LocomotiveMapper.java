package com.network.management.mapper;

import com.network.management.domain.dao.Locomotive;
import com.network.management.domain.dao.User;
import com.network.management.domain.search.LocomotiveSearch;
import com.network.management.domain.search.Page;
import com.network.management.domain.search.UserSearch;

import java.util.List;


/**
 * 机车mapper
 *
 * @author yyc
 * @date 2020/10/16 15:12
 */
public interface LocomotiveMapper {

    /**
     * 插入一条机车数据
     * @param locomotive 机车对象
     * @return 主键id
     */
    Integer insert(Locomotive locomotive);

    /**
     * 修改机车数据
     * @param locomotive 机车信息
     */
    void updateByPrimaryKey(Locomotive locomotive);

    /**
     * 通过主键id删除用户数据
     * @param id 主键id
     */
    void deleteByPrimaryKey(Integer id);

    /**
     * 分页查询机车信息
     * @param search {@link LocomotiveSearch}
     * @return {@link List<Locomotive>}
     */
    List<Locomotive> getByConditions(LocomotiveSearch search);

    /**
     * 查询所有机车数据
     * @return {@link List<Locomotive>}
     */
    List<Locomotive> queryAllLocomotives();
}
