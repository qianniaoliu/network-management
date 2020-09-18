package com.network.management.domain.dao;

import com.network.management.domain.enums.YnEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 巷道图基本信息
 *
 * @author yusheng
 */
@Data
@ApiModel("巷道图基本信息")
public class BordInformation {

    /**
     * 巷道信息主键id
     */
    @ApiModelProperty("巷道信息主键id")
    private Integer id;

    /**
     * 巷道标题
     */
    @ApiModelProperty("巷道标题")
    private String name;

    /**
     * 巷道图url
     */
    @ApiModelProperty("巷道图url")
    private String url;

    /**
     * 有效状态
     */
    @ApiModelProperty("有效状态")
    private Integer yn;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date modified;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date created;

    public BordInformation() {
    }

    public BordInformation(Integer id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public void initCreateInfo(){
        setYn(YnEnum.YES.getCode());
        setCreated(new Date());
        setModified(new Date());
    }

    public void initModifyInfo(){
        setModified(new Date());
    }
}
