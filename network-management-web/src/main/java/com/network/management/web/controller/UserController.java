package com.network.management.web.controller;

import com.network.management.domain.search.UserSearch;
import com.network.management.domain.vo.RegistryVo;
import com.network.management.service.UserService;
import com.network.management.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 登录注册控制器
 *
 * @author yusheng
 */
@RestController
@Api(tags = "用户信息管理")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 注册用户信息
     *
     * @param registryVo 注册对象
     * @return 返回结果
     */
    @PostMapping("/registry")
    @ApiOperation("注册用户信息")
    public Result registry(@RequestBody RegistryVo registryVo) {
        registryVo.check();
        userService.add(registryVo);
        return Result.success(true);
    }

    /**
     * 修改用户信息
     *
     * @param registryVo 注册对象
     * @return 返回结果
     */
    @PostMapping("/modify")
    @ApiOperation("修改用户信息")
    public Result modify(@RequestBody RegistryVo registryVo) {
        registryVo.check();
        userService.update(registryVo);
        return Result.success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除用户信息")
    public Result delete(@RequestParam("userId") Integer userId){
        userService.delete(userId);
        return Result.success(true);
    }

    @PostMapping("/search")
    @ApiOperation("搜索用户信息")
    public Result search(@RequestBody UserSearch search){
        return Result.success(userService.search(search));
    }


    @GetMapping("/get")
    @ApiOperation("获取用户信息")
    public Result get(@RequestParam("userId") Integer userId){
        return Result.success(userService.get(userId));
    }
}
