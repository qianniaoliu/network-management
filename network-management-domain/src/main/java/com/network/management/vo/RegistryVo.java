package com.network.management.vo;

import com.network.management.common.exception.IllegalParamException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: yusheng
 * @date: 2020/9/8 22:39
 */
@Data
public class RegistryVo {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
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
