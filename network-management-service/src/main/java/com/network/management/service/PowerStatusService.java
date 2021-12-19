package com.network.management.service;

import com.network.management.domain.vo.PowerReqVo;
import com.network.management.domain.vo.PowerStatusVo;

/**
 * 电源控制/查询服务接口
 * @author yyc
 * @date 2021/12/19 17:08
 */
public interface PowerStatusService {
    /**
     * 查询电源交直流状态
     * @param powerReqVo 电源信息对象 {@link PowerReqVo}
     * @return {@link PowerStatusVo}
     */
    PowerStatusVo queryPowerStatus(PowerReqVo powerReqVo);

    /**
     * 切换电源交直流
     * @param powerReqVo 电源信息对象 {@link PowerReqVo}
     */
    Boolean exchangePowerStatus(PowerReqVo powerReqVo);
}
