package com.network.management.web.controller;

import com.network.management.domain.search.ProfessionSearch;
import com.network.management.domain.vo.ProfessionVo;
import com.network.management.service.ProfessionService;
import com.network.management.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 职位管理controller
 * @author yyc
 * @date 2021/03/01 11:50
 */
@RestController
@Api(tags = "职位信息管理")
@RequestMapping("/profession")
public class ProfessionController {
    @Resource
    private ProfessionService professionService;

    /**
     * 新增职位信息
     *
     * @param professionVo 职位信息
     * @return 返回结果
     */
    @PostMapping("/add")
    @ApiOperation("新增职位信息")
    public Result add(@RequestBody ProfessionVo professionVo) {
        professionService.add(professionVo);
        return Result.success(true);
    }

    /**
     * 修改职位信息
     *
     * @param professionVo 职位信息
     * @return 返回结果
     */
    @PostMapping("/modify")
    @ApiOperation("修改职位信息")
    public Result modify(@RequestBody ProfessionVo professionVo) {
        professionService.update(professionVo);
        return Result.success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除职位信息")
    public Result delete(@RequestParam("professionId") Integer professionId){
        professionService.delete(professionId);
        return Result.success(true);
    }

    @PostMapping("/search")
    @ApiOperation("搜索职位信息")
    public Result search(@RequestBody ProfessionSearch search){
        return Result.success(professionService.search(search));
    }


    @GetMapping("/get")
    @ApiOperation("获取职位信息")
    public Result get(@RequestParam("professionId") Integer professionId){
        return Result.success(professionService.get(professionId));
    }

    @GetMapping("/queryAll")
    @ApiOperation("查询所有职位信息")
    public Result queryAll(){
        return Result.success(professionService.queryAllProfessions());
    }
}
