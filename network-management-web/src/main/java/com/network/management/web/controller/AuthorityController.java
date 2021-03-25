package com.network.management.web.controller;

import com.network.management.domain.vo.AuthorityVo;
import com.network.management.service.AuthorityService;
import com.network.management.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 授权明细管理Controller
 *
 * @author yyc
 * @date 2021/3/25 16:07
 */
@RestController
@Api(tags = "授权明细")
@RequestMapping("/authority")
public class AuthorityController {
    @Resource
    private AuthorityService authorityService;

    /**
     * 新增授权明细
     *
     * @param authorityVo 授权明细
     * @return 返回结果
     */
    @PostMapping("/add")
    @ApiOperation("新增授权明细")
    public Result add(@RequestBody AuthorityVo authorityVo) {
        authorityService.add(authorityVo);
        return Result.success(true);
    }

    /**
     * 修改授权明细
     *
     * @param authorityVo 授权明细
     * @return 返回结果
     */
    @PostMapping("/modify")
    @ApiOperation("修改授权明细")
    public Result modify(@RequestBody AuthorityVo authorityVo) {
        authorityService.update(authorityVo);
        return Result.success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除授权明细")
    public Result delete(@RequestParam("authorityId") Integer authorityId) {
        authorityService.delete(authorityId);
        return Result.success(true);
    }

    @GetMapping("/queryAuthorityById")
    @ApiOperation("根据授权明细id查询授权明细")
    public Result queryAuthorityById(@RequestParam("authorityId") Integer authorityId) {
        return Result.success(authorityService.queryById(authorityId));
    }

    @GetMapping("/queryAll")
    @ApiOperation("查询所有授权明细")
    public Result queryAll() {
        return Result.success(authorityService.queryAllAuthorityVos());
    }

}
