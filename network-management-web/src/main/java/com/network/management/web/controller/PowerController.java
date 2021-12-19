package com.network.management.web.controller;

import com.network.management.domain.vo.PowerReqVo;
import com.network.management.service.PowerStatusService;
import com.network.management.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 电源管理
 * @author yyc
 * @date 2021/12/19 17:21
 */
@RestController
@RequestMapping("/power")
@Api(tags = "电源管理")
public class PowerController {

    @Autowired
    private PowerStatusService powerStatusService;

    /**
     * 查询电源状态
     * @param powerReqVo {@link PowerReqVo}
     * @return 前端渲染对象
     */
    @PostMapping("/query")
    @ApiOperation("查询电源状态")
    public Result queryPowerStatus(@RequestBody PowerReqVo powerReqVo) {
        return Result.success(powerStatusService.queryPowerStatus(powerReqVo));
    }


    /**
     * 切换电源
     * @param powerReqVo 操作类型
     * @return 前端渲染对象
     */
    @PostMapping("/exchange")
    @ApiOperation("切换电源")
    public Result exchangePower(@RequestBody PowerReqVo powerReqVo) {
        return Result.success(powerStatusService.exchangePowerStatus(powerReqVo));
    }
}
