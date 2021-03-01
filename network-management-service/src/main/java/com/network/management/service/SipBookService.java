package com.network.management.service;

import com.network.management.domain.vo.SipBookVo;

import java.util.List;

/**
 * sip服务类
 * @author yyc
 * @date 2021/2/28 11:50
 */
public interface SipBookService {
    /**
     * 新增sip信息
     *
     * @param sipBookVo sip信息
     * @return sipBook id
     */
    void add(SipBookVo sipBookVo);

    /**
     * 修改sip信息
     *
     * @param sipBookVo sip信息
     */
    void update(SipBookVo sipBookVo);

    /**
     * 删除sip信息
     *
     * @param id sipBook id
     */
    void delete(Integer id);

    /**
     * 查询所有sip信息
     * @return {@link List<SipBookVo>}
     */
    List<SipBookVo> queryAllSip();

    /**
     * 根据通讯录id查询sip账号列表
     * @param bookId 通讯录id
     * @return {@link List<SipBookVo>}
     */
    List<SipBookVo> querySipByBookId(Integer bookId);
}
