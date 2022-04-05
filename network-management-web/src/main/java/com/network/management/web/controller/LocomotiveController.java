package com.network.management.web.controller;

import com.network.management.domain.search.LocomotiveSearch;
import com.network.management.domain.vo.LocomotiveVo;
import com.network.management.service.LocomotiveService;
import com.network.management.service.LocomotiveStatisticsService;
import com.network.management.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 机车管理
 * @author yyc
 * @date 2020/10/17 14:20
 */
@RestController
@RequestMapping("/locomotive")
@Api(tags = "机车数据管理")
public class LocomotiveController {

    @Value("${locomotive_type:1}")
    private String locomotiveType;
    @Autowired
    private List<LocomotiveService> locomotiveServices;
    @Autowired
    private LocomotiveStatisticsService locomotiveStatisticsService;

    /**
     * 获取机车服务对象
     * @return {@link LocomotiveService}
     */
    private LocomotiveService getLocomotiveService(){
        return ListUtils.emptyIfNull(locomotiveServices)
                .stream()
                .filter(s -> Objects.nonNull(s) && s.isSupport(locomotiveType))
                .findAny()
                .orElse(null);
    }

    /**
     * 新增机车基本信息
     *
     * @param locomotiveVo {@link LocomotiveVo}
     * @return 前端渲染对象
     */
    @PostMapping("/save")
    @ApiOperation("新增机车基本信息")
    public Result saveLocomotive(@RequestBody LocomotiveVo locomotiveVo) {
        return Result.success(getLocomotiveService().saveLocomotive(locomotiveVo));
    }

    /**
     * 修改机车基本信息
     *
     * @param locomotiveVo {@link LocomotiveVo}
     * @return 前端渲染对象
     */
    @PostMapping("/modify")
    @ApiOperation("修改机车基本信息")
    public Result modifyLocomotive(@RequestBody LocomotiveVo locomotiveVo) {
        getLocomotiveService().updateLocomotive(locomotiveVo);
        return Result.success(true);
    }

    /**
     * 删除机车
     *
     * @param locomotiveId 机车Id
     * @return 前端渲染对象
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除机车信息")
    @ApiImplicitParam(name = "locomotiveId", value = "机车id", required = true)
    public Result deleteLocomotive(@RequestParam("locomotiveId") Integer locomotiveId) {
        getLocomotiveService().delete(locomotiveId);
        return Result.success(true);
    }

    /**
     * 查询基站对应机车数据以及机车状态
     *
     * @return 前端渲染对象
     */
    @GetMapping("/queryLocomotiveStatus")
    @ApiOperation("查询基站对应机车数据以及机车状态")
    public Result queryLocomotiveStatus() {
        return Result.success(getLocomotiveService().queryLocomotiveStatus());
    }

    /**
     * 分页查询机车数据
     * @return
     */
    @PostMapping("/search")
    @ApiOperation("分页查询机车")
    public Result search(@RequestBody LocomotiveSearch search){
        return Result.success(getLocomotiveService().search(search));
    }

    /**
     * 查询机车统计数量
     *
     * @return 前端渲染对象
     */
    @GetMapping("/queryLocomotiveStatisticsNumbers")
    @ApiOperation("查询机车统计数量")
    public Result queryLocomotiveStatisticsNumbers() {
        return Result.success(locomotiveStatisticsService.queryLocomotiveStatisticsNumbers());
    }
}
