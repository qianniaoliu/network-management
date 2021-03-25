package com.network.management.web.controller;

import com.network.management.domain.vo.AuthorityRelationVo;
import com.network.management.service.AuthorityRelationService;
import com.network.management.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户授权明细管理Controller
 *
 * @author yyc
 * @date 2021/3/25 16:07
 */
@RestController
@Api(tags = "用户授权明细")
@RequestMapping("/authorityRelation")
public class AuthorityRelationController {
    @Resource
    private AuthorityRelationService authorityRelationService;

    /**
     * 新增授权明细
     *
     * @param authorityRelationVos 用户授权明细列表
     * @return 返回结果
     */
    @PostMapping("/add")
    @ApiOperation("新增用户授权明细")
    public Result add(@RequestBody List<AuthorityRelationVo> authorityRelationVos) {
        authorityRelationService.add(authorityRelationVos);
        return Result.success(true);
    }

    /**
     * 修改用户授权明细
     *
     * @param authorityRelationVos 用户授权明细
     * @return 返回结果
     */
    @PostMapping("/modify")
    @ApiOperation("修改用户授权明细")
    public Result modify(@RequestBody List<AuthorityRelationVo> authorityRelationVos) {
        authorityRelationService.updateAuthorityRelationVos(authorityRelationVos);
        return Result.success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("根据用户id删除用户授权明细")
    public Result delete(@RequestParam("userId") Integer userId) {
        authorityRelationService.deleteByUserId(userId);
        return Result.success(true);
    }

    @GetMapping("/queryAuthorityRelationByUserId")
    @ApiOperation("根据用户id查询用户授权明细")
    public Result queryAuthorityRelationByUserId(@RequestParam("userId") Integer userId) {
        return Result.success(authorityRelationService.queryAllAuthorityRelationVos(userId));
    }

}
