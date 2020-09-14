package com.network.management.web.controller;

import com.network.management.domain.vo.RegistryVo;
import com.network.management.service.UserService;
import com.network.management.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录注册控制器
 *
 * @author yusheng
 */
@RestController
@Api(tags = "登录注册管理")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 注册用户信息
     * @param registryVo 注册对象
     * @return 返回结果
     */
    @PostMapping("/registry")
    @ApiOperation("注册用户信息")
    @ApiImplicitParam(name = "registryVo", value = "用户信息", required = true)
    public Result registry(@RequestBody RegistryVo registryVo){
        registryVo.check();
        userService.add(registryVo);
        return Result.success(null);
    }
}
