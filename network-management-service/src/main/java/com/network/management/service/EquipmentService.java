package com.network.management.service;

import com.network.management.domain.dao.Equipment;
import com.network.management.domain.excel.DeviceStatusData;
import com.network.management.domain.search.EquipmentStatusSearch;
import com.network.management.domain.search.Page;
import com.network.management.domain.vo.DeviceStatusVo;

import java.util.List;
import java.util.Set;

/**
 * 设备信息服务
 *
 * @author yusheng
 */
public interface EquipmentService {

    /**
     * 新增设备信息
     *
     * @param equipment 设备信息
     * @return 设备id
     */
    Integer add(Equipment equipment);

    /**
     * 修改设备信息
     *
     * @param equipment 设备信息
     */
    void update(Equipment equipment);

    /**
     * 删除设备信息
     *
     * @param ids 设备id集合
     */
    void delete(Set<Integer> ids);

    /**
     * 获取单个设备信息
     *
     * @param id 设备id
     * @return 设备信息
     */
    Equipment get(Integer id);

    /**
     * 通过巷道图id获取所有的设备信息
     *
     * @param bordId 巷道图id
     * @return 设备信息集合
     */
    List<Equipment> getByBordId(Integer bordId);

    /**
     * 查询设备信息以及状态
     *
     * @param id 设备id
     * @return {@link DeviceStatusVo}
     */
    DeviceStatusVo<?> queryStatus(Integer id);

    /**
     * 分页查询设备状态
     * @param param 搜索条件
     * @return 分页信息
     */
    Page<DeviceStatusVo> searchDeviceStatus(EquipmentStatusSearch param);

    /**
     * 查询导出的数据
     * @param param 查询条件
     * @return 导出数据
     */
    DeviceStatusData searchExportData(EquipmentStatusSearch param);


}
