package com.network.management.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * 基础进站出站数据
 *
 * @author <a href="mailto:cereb.shen@gmail.com">shenlong</a>
 */
@Data
public class LocomotiveAccessDataVo {

    /**
     * 进出站数据，按照指定顺序排序
     */
    private List<LocomotiveData> locomotiveDatas;


    /**
     * 南翼明细数据
     */
    private List<String> southParticulars;

    /**
     * 北翼明细数据
     */
    private List<String> northParticulars;
}
