package com.network.management.web.controller;

import com.google.common.collect.Sets;
import com.network.management.domain.dao.Equipment;
import com.network.management.service.EquipmentService;
import com.network.management.web.vo.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 设备信息控制器
 *
 * @author yusheng
 */
@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    /**
     * 获取单个设备信息
     * @param equipmentId 设备信息id
     * @return 前端渲染对象
     */
    @GetMapping("/get")
    public Result equipmentInfo(@RequestParam("equipmentId") Integer equipmentId){
        return Result.success(equipmentService.get(equipmentId));
    }


    /**
     * 新增设备基本信息
     * @param equipment 设备信息
     * @return 前端渲染对象
     */
    @PostMapping("/save")
    public Result saveEquipment(@RequestBody Equipment equipment){
        equipmentService.add(equipment);
        return Result.success(null);
    }

    /**
     * 修改设备基本信息
     * @param equipment 设备信息
     * @return 前端渲染对象
     */
    @PostMapping("/modify")
    public Result modifyEquipment(@RequestBody Equipment equipment){
        equipmentService.update(equipment);
        return Result.success(null);
    }

    /**
     * 删除设备基本信息
     * @param equipmentId 设备Id
     * @return 前端渲染对象
     */
    @DeleteMapping("/delete")
    public Result deleteEquipment(@RequestParam("equipmentId") Integer equipmentId){
        equipmentService.delete(Sets.newHashSet(equipmentId));
        return Result.success(null);
    }

}
