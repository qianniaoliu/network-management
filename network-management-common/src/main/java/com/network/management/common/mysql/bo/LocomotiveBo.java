package com.network.management.common.mysql.bo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocomotiveBo {
    /**
     * 机车imsi
     */
    private String imsi;
    /**
     * 机车ip
     */
    private String ueIp;
    /**
     * 基站ip
     */
    private String eNodeBIP;

    /**
     * 机车状态
     */
    private Integer status;
}
