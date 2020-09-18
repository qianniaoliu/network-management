package com.network.management.domain.vo;

import com.network.management.common.exception.IllegalParamException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yusheng
 */
@Data
@ApiModel("注册信息")
public class RegistryVo {

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 确认密码
     */
    @ApiModelProperty("二次密码")
    private String confirmPassword;


    public void check(){
        if(StringUtils.isBlank(this.userName)){
            throw new IllegalParamException("用户名不能为空");
        }

        if(StringUtils.isBlank(this.password)){
            throw new IllegalParamException("密码不能为空");
        }

        if(StringUtils.isBlank(this.confirmPassword)){
            throw new IllegalParamException("二次确认密码不能为空");
        }

        if(!this.password.equals(this.confirmPassword)){
            throw new IllegalParamException("两次输入的密码不相等");
        }
    }

}
