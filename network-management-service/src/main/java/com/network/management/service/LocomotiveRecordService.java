package com.network.management.service;

import com.network.management.domain.vo.LocomotiveRecordVo;

import java.util.List;

/**
 * 机车记录服务
 *
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
public interface LocomotiveRecordService {

    /**
     * 保存机车记录
     *
     * @param recordRequest 机车记录对象
     * @return true：成功，false：失败
     */
    boolean save(LocomotiveRecordVo recordRequest);

    /**
     * 查询机车记录
     *
     * @param currentPage 当前查询位置索引
     * @param pageSize     分页大小
     * @return 机车记录
     */
    List<LocomotiveRecordVo> queryLocomotiveRecords(Integer currentPage, Integer pageSize);
}
