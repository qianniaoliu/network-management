package com.network.management.mapper;

import com.network.management.domain.dao.StationStatus;
import com.network.management.domain.search.EquipmentStatusSearch;

import java.util.List;

/**
 * 基站状态数据mapper
 * @author yyc
 * @date 2020/9/13 16:55
 */
public interface StationStatusMapper {

    Integer insert(StationStatus stationStatus);

    StationStatus getLatestByIp(String ip);

    /**
     * 分页查询基站状态
     * @param condition 条件
     * @return
     */
    List<StationStatus> getByConditions(EquipmentStatusSearch condition);

    /**
     * 查询总数
     * @param condition 条件
     * @return
     */
    Integer count(EquipmentStatusSearch condition);

    /**
     * 删除当前时间30天前的数据
     * @return
     */
    Integer deleteBefore30Days();

}
