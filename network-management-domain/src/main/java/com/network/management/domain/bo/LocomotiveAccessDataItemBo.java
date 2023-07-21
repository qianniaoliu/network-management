package com.network.management.domain.bo;

import lombok.Data;

/**
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
@Data
public class LocomotiveAccessDataItemBo {

    /**
     * 时间
     */
    private String date;

    /**
     * 出场/入场
     */
    private String type;
}
