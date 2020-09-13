package com.network.management.bo;

import lombok.Data;

/**
 * 其他设备状态数据类
 * @author yyc
 * @date 2020/9/12 22:54
 */
@Data
public class DeviceStatusBo {
    /**
     * ping连接状态 {@link com.network.management.enums.YnEnum}
     */
    private Integer pingStatus;
}
