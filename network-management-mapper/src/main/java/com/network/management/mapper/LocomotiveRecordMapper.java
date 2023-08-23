package com.network.management.mapper;

import com.network.management.domain.dao.LocomotiveRecord;

import java.util.List;

/**
 * 机车记录数据库mapper层
 *
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
public interface LocomotiveRecordMapper {

    /**
     * 插入一条机车记录
     * @param locomotiveRecord 机车记录
     * @return 主键
     */
    Integer insert(LocomotiveRecord locomotiveRecord);

    /**
     * 查询所有的机车记录
     * @return 所有机车记录
     */
    List<LocomotiveRecord> queryLocomotiveRecords(Integer currentIndex, Integer pageSize);
}
