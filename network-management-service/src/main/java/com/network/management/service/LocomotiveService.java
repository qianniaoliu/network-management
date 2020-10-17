package com.network.management.service;

import com.network.management.domain.search.LocomotiveSearch;
import com.network.management.domain.search.Page;
import com.network.management.domain.vo.LocomotiveVo;

import java.util.List;
import java.util.Map;

/**
 * 机车服务类
 * @author yyc
 * @date 2020/10/14 22:39
 */
public interface LocomotiveService {
    /**
     * 查询基站下机车状态
     * coreNetIp 核心网ip
     * @return {@link Map<String, List<LocomotiveVo>>}
     */
    Map<String, List<LocomotiveVo>> queryLocomotiveStatus();

    /**
     * 新增机车
     * @param locomotiveVo
     * @return
     */
    Integer saveLocomotive(LocomotiveVo locomotiveVo);

    /**
     * 删除机车
     * @param id
     * @return
     */
    void delete(Integer id);

    /**
     * 新增机车
     * @param locomotiveVo
     * @return
     */
    void updateLocomotive(LocomotiveVo locomotiveVo);

    /**
     * 查询所有机车数据
     * @return {@link List<LocomotiveVo>}
     */
    List<LocomotiveVo> queryAllLocomotiveVos();

    /**
     * 分页查询机车数据
     * @param search {@link LocomotiveSearch}
     * @return {@link List<LocomotiveVo>}
     */
    Page<LocomotiveVo> search(LocomotiveSearch search);
}
