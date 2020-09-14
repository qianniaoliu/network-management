package com.network.management.domain.bo;

import lombok.Data;

/**
 * 其他设备状态数据类
 * @author yyc
 * @date 2020/9/12 22:54
 */
@Data
public class OtherDeviceStatusBo {
    /**
     * ping连接状态 {@link com.network.management.domain.enums.YnEnum}
     */
    private Integer status;
}
