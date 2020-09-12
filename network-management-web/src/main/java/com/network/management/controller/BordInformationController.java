package com.network.management.controller;

import com.network.management.BordInformation;
import com.network.management.exception.IllegalParamException;
import com.network.management.service.BordInformationService;
import com.network.management.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * 巷道图信息控制器
 *
 * @author yusheng
 */
@RestController
@RequestMapping("/bord")
public class BordInformationController {

    @Autowired
    private BordInformationService bordInformationService;


    /**
     * 上传巷道图到本地（临时方案），目前只会上传一张图片
     * @param bordFile 巷道图图片
     * @param bordName 巷道图标题
     * @param bordId 巷道图id
     * @return 前端返回信息
     */
    @PostMapping("/info/save")
    public Result uploadFile(@RequestParam(value = "bordFile", required = false) MultipartFile bordFile,
                             @RequestParam(value = "bordName", required = false) String bordName,
                             @RequestParam(value = "bordId", required = false) Integer bordId){
        String bordFileUrl = null;
        if(Objects.nonNull(bordFile)) {
            String rootPath = System.getProperty("user.dir") + "/static/img/";
            File imgDir = new File(rootPath);
            if (!imgDir.exists()) {
                imgDir.mkdirs();
            }
            String bordFileName = "bord-img.jpg";
            File imgFile = new File(imgDir, bordFileName);
            try {
                bordFile.transferTo(imgFile);
                bordFileUrl = "/" + bordFileName;
            } catch (IOException e) {
                throw new IllegalParamException("上传图片失败!");
            }
        }
        BordInformation bordInformation =
                new BordInformation(bordId, bordName, bordFileUrl);
        bordInformationService.save(bordInformation);
        return Result.success(null);
    }






}
