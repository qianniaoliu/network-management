package com.network.management.service;

import com.network.management.domain.search.ShareMessageSearch;
import com.network.management.domain.vo.ShareMessageVo;

import java.util.List;

/**
 * @author yusheng
 */
public interface ShareMessageService {

    /**
     * 新增共享消息
     * @param shareMessageVo
     * @return
     */
    Integer add(ShareMessageVo shareMessageVo);

    /**
     * 搜索查询消息
     * @param shareMessageSearch 搜索对象
     * @return 消息列表
     */
    List<ShareMessageVo> search(ShareMessageSearch shareMessageSearch);
}
