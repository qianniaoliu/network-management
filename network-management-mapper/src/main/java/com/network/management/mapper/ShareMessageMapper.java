package com.network.management.mapper;

import com.network.management.domain.dao.ShareMessage;
import com.network.management.domain.search.ShareMessageSearch;

import java.util.List;

/**
 * 共享信息mapper
 *
 * @author yusheng
 */
public interface ShareMessageMapper {

    /**
     * 新增共享信息
     * @param shareMessage 共享信息
     * @return 主键id
     */
    Integer insert(ShareMessage shareMessage);

    /**
     * 搜索查询消息
     * @param shareMessageSearch 搜索对象
     * @return 消息列表
     */
    List<ShareMessage> search(ShareMessageSearch shareMessageSearch);
}
