package com.network.management.controller;

import com.network.management.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 巷道图信息控制器
 *
 * @author yusheng
 */
@RestController
@RequestMapping("/bord")
public class BordInformationController {


    @PostMapping("/file/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file){

        return Result.success(null);
    }


}
