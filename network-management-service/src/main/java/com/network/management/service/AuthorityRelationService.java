package com.network.management.service;

import com.network.management.domain.vo.AuthorityRelationVo;

import java.util.List;

/**
 * 用户授权明细关系服务类
 *
 * @author yyc
 * @date 2021/3/25 11:50
 */
public interface AuthorityRelationService {
    /**
     * 新增用户授权明细
     *
     * @param authorityRelationVos 用户授权明细
     */
    void add(List<AuthorityRelationVo> authorityRelationVos);

    /**
     * 删除授权明细
     *
     * @param userId 用户id
     */
    void deleteByUserId(Integer userId);

    /**
     * 更新用户授权关系
     *
     * @param authorityRelationVos {@link List<AuthorityRelationVo>}
     */
    void updateAuthorityRelationVos(List<AuthorityRelationVo> authorityRelationVos);

    /**
     * 根据用户id查询所有授权明细
     *
     * @return {@link List<AuthorityRelationVo>}
     */
    List<AuthorityRelationVo> queryAllAuthorityRelationVos(Integer userId);
}
