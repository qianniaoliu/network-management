package com.network.management.service;

import com.network.management.domain.bo.DeviceStatusResultBo;
import com.network.management.domain.search.EquipmentStatusSearch;

/**
 * @author yusheng
 */
public interface DeviceStatusStrategy {

    DeviceStatusResultBo search(EquipmentStatusSearch search);

    Boolean support(Integer equipmentType);
}
