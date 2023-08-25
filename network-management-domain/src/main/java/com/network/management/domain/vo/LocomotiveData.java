package com.network.management.domain.vo;

import lombok.Data;

/**
 * 统计数据实体
 *
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
@Data
public class LocomotiveData implements Comparable<LocomotiveData> {

    /**
     * 头
     */
    private String title;

    /**
     * 入场/出场
     */
    private String type;

    /**
     * 天统计
     */
    private int dayStatistics;

    /**
     * 周统计
     */
    private int weekStatistics;

    /**
     * 月统计
     */
    private int monthStatistics;

    @Override
    public int compareTo(LocomotiveData o) {
        return o.title.compareTo(this.title);
    }
}
