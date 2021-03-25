package com.network.management.mapper;

import com.network.management.domain.dao.Authority;

import java.util.List;

/**
 * 授权明细mapper
 * @author yyc
 * @date 2021/3/25 11:31
 */
public interface AuthorityMapper {

    /**
     * 插入授权明细
     * @param authority {@link Authority}
     * @return 返回主键id
     */
    Integer insert(Authority authority);

    /**
     * 修改授权明细
     * @param authority 授权明细
     */
    void updateByPrimaryKeySelective(Authority authority);

    /**
     * 通过主键id删除授权明细
     * @param id 主键id
     */
    void deleteByPrimaryKey(Integer id);

    /**
     * 查询所有授权明细
     */
    List<Authority> queryAllAuthorities();

    /**
     * 通过主键id查询授权明细
     * @param id 主键id
     */
    Authority selectByPrimaryKey(Integer id);
}
