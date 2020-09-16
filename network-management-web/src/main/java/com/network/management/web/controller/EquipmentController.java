package com.network.management.web.controller;

import com.google.common.collect.Sets;
import com.network.management.domain.dao.Equipment;
import com.network.management.service.EquipmentService;
import com.network.management.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 设备信息控制器
 *
 * @author yusheng
 */
@RestController
@RequestMapping("/equipment")
@Api(tags = "设备信息管理")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    /**
     * 获取单个设备信息
     *
     * @param equipmentId 设备信息id
     * @return 前端渲染对象
     */
    @GetMapping("/get")
    @ApiOperation("获取单个设备信息")
    @ApiImplicitParam(name = "equipmentId", value = "设备id", required = true)
    public Result equipmentInfo(@RequestParam("equipmentId") Integer equipmentId) {
        return Result.success(equipmentService.get(equipmentId));
    }


    /**
     * 新增设备基本信息
     *
     * @param equipment 设备信息
     * @return 前端渲染对象
     */
    @PostMapping("/save")
    @ApiOperation("新增设备基本信息")
    @ApiImplicitParam(name = "equipment", value = "设备信息", required = true)
    public Result saveEquipment(@RequestBody Equipment equipment) {
        return Result.success(equipmentService.add(equipment));
    }

    /**
     * 修改设备基本信息
     *
     * @param equipment 设备信息
     * @return 前端渲染对象
     */
    @PostMapping("/modify")
    @ApiOperation("修改设备基本信息")
    @ApiImplicitParam(name = "equipment", value = "设备信息", required = true)
    public Result modifyEquipment(@RequestBody Equipment equipment) {
        equipmentService.update(equipment);
        return Result.success(null);
    }

    /**
     * 删除设备基本信息
     *
     * @param equipmentId 设备Id
     * @return 前端渲染对象
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除设备基本信息")
    @ApiImplicitParam(name = "equipmentId", value = "设备id", required = true)
    public Result deleteEquipment(@RequestParam("equipmentId") Integer equipmentId) {
        equipmentService.delete(Sets.newHashSet(equipmentId));
        return Result.success(null);
    }

    /**
     * 获取单个设备信息以及状态数据
     *
     * @param equipmentId 设备信息id
     * @return 前端渲染对象
     */
    @GetMapping("/queryStatus")
    @ApiOperation("获取单个设备信息")
    @ApiImplicitParam(name = "equipmentId", value = "设备id", required = true)
    public Result queryStatus(@RequestParam("equipmentId") Integer equipmentId) {
        return Result.success(equipmentService.queryStatus(equipmentId));
    }

}
