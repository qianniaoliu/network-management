package com.network.management.mapper;

import com.network.management.domain.dao.OtherDeviceStatus;
import com.network.management.domain.search.EquipmentStatusSearch;

import java.util.List;
import java.util.Map;

/**
 * 其他设备状态数据mapper
 *
 * @author yyc
 * @date 2020/9/13 16:55
 */
public interface OtherDeviceStatusMapper {

    Integer insert(OtherDeviceStatus otherDeviceStatus);

    OtherDeviceStatus getLatestByIp(String ip);

    List<OtherDeviceStatus> getByConditions(EquipmentStatusSearch condition);

    Integer count(EquipmentStatusSearch condition);

}
