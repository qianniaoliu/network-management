package com.network.management.web.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.google.common.collect.Sets;
import com.network.management.common.CommonUtils;
import com.network.management.common.exception.IllegalParamException;
import com.network.management.domain.dao.Equipment;
import com.network.management.domain.excel.DeviceStatusData;
import com.network.management.domain.search.EquipmentStatusSearch;
import com.network.management.service.EquipmentService;
import com.network.management.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Objects;

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
    @ApiImplicitParam(name = "equipmentId", value = "设备id", required = true, dataType = "integer")
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
    public Result modifyEquipment(@RequestBody Equipment equipment) {
        equipmentService.update(equipment);
        return Result.success(true);
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
        return Result.success(true);
    }

    /**
     * 获取单个设备信息以及状态数据
     *
     * @param equipmentId 设备信息id
     * @return 前端渲染对象
     */
    @GetMapping("/queryStatus")
    @ApiOperation("获取单个设备状态")
    @ApiImplicitParam(name = "equipmentId", value = "设备id", required = true)
    public Result queryStatus(@RequestParam("equipmentId") Integer equipmentId) {
        return Result.success(equipmentService.queryStatus(equipmentId));
    }

    /**
     * 自定义上传设备图标
     *
     * @param equipmentFile 设备图标
     * @return 前端返回信息
     */
    @PostMapping("/file/upload")
    @ApiOperation("上传设备图标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "equipmentFile", value = "设备图标")
    })
    public Result<String> uploadFile(@RequestParam(value = "equipmentFile") MultipartFile equipmentFile) {
        String equipmentFileUrl = null;
        if (Objects.nonNull(equipmentFile)) {
            String imgDirPath = CommonUtils.getImgDirPath();
            String rootPath = imgDirPath + "/static/img/";
            File imgDir = new File(rootPath);
            if (!imgDir.exists()) {
                imgDir.mkdirs();
            }
            String bordFileName = "equipment-" + new Date().getTime() + ".jpg";
            File imgFile = new File(imgDir, bordFileName);
            try {
                equipmentFile.transferTo(imgFile);
                equipmentFileUrl = "/" + bordFileName;
            } catch (IOException e) {
                throw new IllegalParamException("上传图片失败!");
            }
        }
        return Result.success(equipmentFileUrl);
    }

    /**
     * 搜索设备状态信息
     * @param search 分页查询条件
     * @return 返回
     */
    @PostMapping("/status/search")
    @ApiOperation("搜索设备状态信息")
    public Result search(@RequestBody EquipmentStatusSearch search){
        search.checkParams();
        return Result.success(equipmentService.searchDeviceStatus(search));
    }

    /**
     * 导出设备状态信息
     * @param search 查询条件
     * @param response 响应对象
     * @return 返回
     */
    @PostMapping("/status/export")
    @ApiOperation("导出设备状态信息")
    public void export(@RequestBody EquipmentStatusSearch search, HttpServletResponse response) throws Exception{
        search.checkExportParams();
        DeviceStatusData deviceStatusData = equipmentService.searchExportData(search);
        if(CollectionUtils.isEmpty(deviceStatusData.getData())){
            return;
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("设备状态数据", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 16);
        headWriteCellStyle.setWriteFont(headWriteFont);

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        EasyExcel.write(response.getOutputStream(), deviceStatusData.getClazz())
                .excelType(ExcelTypeEnum.XLSX)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("设备状态数据")
                .doWrite(deviceStatusData.getData());
    }
}
