package com.network.management.mapper;

import com.network.management.domain.dao.AuthorityRelation;

import java.util.List;

/**
 * 用户授权明细mapper
 * @author yyc
 * @date 2021/3/25 11:31
 */
public interface AuthorityRelationMapper {

    /**
     * 插入用户授权明细
     * @param authorityRelation {@link AuthorityRelation}
     * @return 返回主键id
     */
    Integer insert(AuthorityRelation authorityRelation);

    /**
     * 通过用户id删除用户授权明细
     * @param userId 用户id
     */
    void deleteByUserId(Integer userId);

    /**
     * 通过用户id查询用户授权明细
     */
    List<AuthorityRelation> queryAuthorityRelationByUserId(Integer userId);
}
