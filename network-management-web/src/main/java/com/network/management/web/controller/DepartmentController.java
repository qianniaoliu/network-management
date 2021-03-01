package com.network.management.web.controller;

import com.network.management.domain.search.DepartmentSearch;
import com.network.management.domain.vo.DepartmentVo;
import com.network.management.service.DepartmentService;
import com.network.management.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 部门管理controller
 * @author yyc
 * @date 2021/03/01 11:50
 */
@RestController
@Api(tags = "部门信息管理")
@RequestMapping("/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    /**
     * 新增部门信息
     *
     * @param departmentVo 部门信息
     * @return 返回结果
     */
    @PostMapping("/add")
    @ApiOperation("新增部门信息")
    public Result add(@RequestBody DepartmentVo departmentVo) {
        departmentService.add(departmentVo);
        return Result.success(true);
    }

    /**
     * 修改部门信息
     *
     * @param departmentVo 部门信息
     * @return 返回结果
     */
    @PostMapping("/modify")
    @ApiOperation("修改部门信息")
    public Result modify(@RequestBody DepartmentVo departmentVo) {
        departmentService.update(departmentVo);
        return Result.success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除部门信息")
    public Result delete(@RequestParam("departmentId") Integer departmentId){
        departmentService.delete(departmentId);
        return Result.success(true);
    }

    @PostMapping("/search")
    @ApiOperation("搜索部门信息")
    public Result search(@RequestBody DepartmentSearch search){
        return Result.success(departmentService.search(search));
    }


    @GetMapping("/get")
    @ApiOperation("获取部门信息")
    public Result get(@RequestParam("departmentId") Integer departmentId){
        return Result.success(departmentService.get(departmentId));
    }

    @GetMapping("/queryAll")
    @ApiOperation("查询所有部门信息")
    public Result queryAll(){
        return Result.success(departmentService.queryAllDepartment());
    }
}
