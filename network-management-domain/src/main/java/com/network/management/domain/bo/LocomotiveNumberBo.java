package com.network.management.domain.bo;

import lombok.Data;

/**
 * 机车数量统计数据类
 * @author yyc
 * @date 2020/12/31 19:12
 */
@Data
public class LocomotiveNumberBo {
    /**
     * 机车总数
     */
    private Integer total;
    /**
     * 机车昨天总数
     */
    private Integer yesterday;
    /**
     * 机车今天总数
     */
    private Integer today;
}
