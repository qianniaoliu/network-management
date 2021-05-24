package com.network.management.web.controller;

import com.network.management.domain.search.ShareMessageSearch;
import com.network.management.service.ShareMessageService;
import com.network.management.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yusheng
 */
@RestController
@RequestMapping("/shareMessage")
@Api(tags = "共享消息管理")
public class ShareMessageController {

    @Autowired
    private ShareMessageService shareMessageService;

    @PostMapping("/query")
    @ApiOperation("查询历史消息")
    public Result query(@RequestBody ShareMessageSearch search){
        return Result.success(shareMessageService.search(search));
    }
}
