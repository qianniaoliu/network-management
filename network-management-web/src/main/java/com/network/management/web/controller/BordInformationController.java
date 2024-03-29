package com.network.management.web.controller;

import com.network.management.common.CommonUtils;
import com.network.management.common.exception.IllegalParamException;
import com.network.management.domain.dao.BordInformation;
import com.network.management.domain.vo.BordInformationAggregation;
import com.network.management.service.BordInformationService;
import com.network.management.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * 巷道图信息控制器
 *
 * @author yusheng
 */
@RestController
@RequestMapping("/bord")
@Api(tags = "巷道图管理")
public class BordInformationController {

    private final BordInformationService bordInformationService;

    public BordInformationController(BordInformationService bordInformationService) {
        this.bordInformationService = bordInformationService;
    }


    /**
     * 上传巷道图到本地（临时方案），目前只会上传一张图片
     *
     * @param bordFile 巷道图图片
     * @return 前端返回信息
     */
    @PostMapping("/file/upload")
    @ApiOperation("上传巷道图图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bordFile", value = "巷道图文件")
    })
    public Result uploadFile(@RequestParam(value = "bordFile") MultipartFile bordFile) {
        String bordFileUrl = null;
        if (Objects.nonNull(bordFile)) {
            String imgDirPath = CommonUtils.getImgDirPath();
            String rootPath = imgDirPath + "/static/img/";
            File imgDir = new File(rootPath);
            if (!imgDir.exists()) {
                imgDir.mkdirs();
            }
            String bordFileName = "bord-" + new Date().getTime() + ".jpg";
            File imgFile = new File(imgDir, bordFileName);
            try {
                bordFile.transferTo(imgFile);
                bordFileUrl = "/" + bordFileName;
            } catch (IOException e) {
                throw new IllegalParamException("上传图片失败!");
            }
        }
        BordInformation bordInformation =
                new BordInformation(null, null, bordFileUrl);
        bordInformationService.save(bordInformation);
        return Result.success(bordInformation);
    }

    /**
     * 保存巷道图基本信息
     *
     * @param bordName 巷道图标题
     * @return 前端返回信息
     */
    @PostMapping("/info/save")
    @ApiOperation("保存巷道图名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bordName", value = "巷道图名称")
    })
    public Result save(@RequestParam(value = "bordName") String bordName) {
        BordInformation bordInformation =
                new BordInformation(null, bordName);
        bordInformationService.save(bordInformation);
        return Result.success(bordInformation);
    }


    /**
     * 获取巷道图全部信息
     *
     * @return 巷道图全量信息
     */
    @GetMapping("/info/all")
    @ApiOperation("根据巷道图id获取巷道图全量信息")
    public Result infoAll() {
        return Result.success(bordInformationService.getAll());
    }


    /**
     * 保存整张巷道图信息，包括巷道图基本信息，设备信息，设备之间映射关系
     *
     * @param bordInformationAggregation 所有的巷道图信息
     * @return 前端渲染对象
     */
    @PostMapping("/save/all")
    @ApiOperation("保存整张巷道图信息，包括巷道图基本信息，设备信息，设备之间映射关系")
    public Result saveAll(@RequestBody BordInformationAggregation bordInformationAggregation) {
        bordInformationService.updateAll(bordInformationAggregation);
        return Result.success(true);
    }


    /**
     * 获取巷道图列表信息
     *
     * @return 巷道图全量信息
     */
    @GetMapping("/list/all")
    @ApiOperation("获取巷道图列表信息")
    public Result listAll() {
        return Result.success(bordInformationService.selectAll());
    }


    /**
     * 修改巷道图信息
     *
     * @return 是否成功
     */
    @PostMapping("/info/modify")
    @ApiOperation("修改巷道图基本信息")
    public Result modify(@RequestBody BordInformation bordInformation) {
        bordInformationService.save(bordInformation);
        return Result.success(bordInformation);
    }
}
