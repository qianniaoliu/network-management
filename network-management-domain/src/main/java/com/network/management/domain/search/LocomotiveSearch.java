package com.network.management.domain.search;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

/**
 * 机车条件查询类
 * @author yyc
 * @date 2020/10/17 13:39
 */
@Data
@ApiModel("设备状态搜索对象")
public class LocomotiveSearch {

    /**
     * 机车ip
     */
    @ApiModelProperty("机车ip")
    private String ueIp;

    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize;

    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private Integer currentPage;

    /**
     * 获取起始位置
     * @return
     */
    private Integer getStartIndex() {
        if (Objects.isNull(currentPage)
                || Objects.isNull(pageSize)) {
            return 0;
        }
        return (currentPage - 1) * pageSize;
    }
}
