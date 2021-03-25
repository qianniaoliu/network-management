package com.network.management.service;

import com.network.management.domain.vo.AuthorityVo;

import java.util.List;

/**
 * 授权明细服务类
 *
 * @author yyc
 * @date 2021/3/25 11:50
 */
public interface AuthorityService {
    /**
     * 新增授权明细
     *
     * @param authorityVo 授权明细
     */
    void add(AuthorityVo authorityVo);

    /**
     * 修改授权明细
     *
     * @param authorityVo 授权明细
     */
    void update(AuthorityVo authorityVo);

    /**
     * 删除授权明细
     *
     * @param id 授权id
     */
    void delete(Integer id);

    /**
     * 查询所有授权明细
     * @return {@link List<AuthorityVo>}
     */
    List<AuthorityVo> queryAllAuthorityVos();

    /**
     * 根据主键id查询授权明细
     * @param id 主键id
     * @return {@link AuthorityVo}
     */
    AuthorityVo queryById(Integer id);
}
