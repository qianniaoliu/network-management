package com.network.management.service;

import com.network.management.domain.vo.DeviceStatusVo;

/**
 * 设备状态查询服务类
 *
 * @author yyc
 * @date 2020/9/16 22:02
 */
public interface DeviceStatusService {
    /**
     * 查询设备信息以及状态
     *
     * @param id 设备id
     * @return {@link DeviceStatusVo}
     */
    DeviceStatusVo<?> queryDeviceStatus(Integer id);
}
