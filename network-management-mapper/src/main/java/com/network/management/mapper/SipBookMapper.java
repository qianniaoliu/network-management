package com.network.management.mapper;

import com.network.management.domain.dao.SipBook;

import java.util.List;

/**
 * sipNumber mapper
 * @author yyc
 * @date 2021/2/27 16:37
 */
public interface SipBookMapper {

    /**
     * 插入通讯录信息
     * @param sipBook {@link SipBook}
     * @return 返回主键id
     */
    Integer insert(SipBook sipBook);

    /**
     * 删除通讯录sipNumber
     * @param sipBook
     */
    void updateByPrimaryKeySelective(SipBook sipBook);

    /**
     * 删除通讯录sipNumber
     * @param id 主键id
     */
    void deleteByPrimaryKey(Integer id);

    /**
     * 查询指定通讯录下的所有sipNumber
     * @return {@link List<SipBook>}
     */
    List<SipBook> querySipByBookId(Integer bookId);

    /**
     * 查询所有sipBook
     * @return {@link List<SipBook>}
     */
    List<SipBook> queryAllSip();

    /**
     * 根据sip查询SipBook对象
     * @return {@link SipBook}
     */
    SipBook querySipBookBySip(Integer sip);
}
