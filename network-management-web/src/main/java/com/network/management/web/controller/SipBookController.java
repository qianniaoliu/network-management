package com.network.management.web.controller;

import com.network.management.domain.vo.SipBookVo;
import com.network.management.service.SipBookService;
import com.network.management.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * sip账号管理controller
 * @author yyc
 * @date 2021/03/01 13:39
 */
@RestController
@Api(tags = "sip账号信息管理")
@RequestMapping("/sipBook")
public class SipBookController {
    @Resource
    private SipBookService sipBookService;

    /**
     * 新增sip账号
     *
     * @param sipBookVo sip账号
     * @return 返回结果
     */
    @PostMapping("/add")
    @ApiOperation("新增sip账号")
    public Result add(@RequestBody SipBookVo sipBookVo) {
        sipBookService.add(sipBookVo);
        return Result.success(true);
    }

    /**
     * 修改sip账号
     *
     * @param sipBookVo sip账号
     * @return 返回结果
     */
    @PostMapping("/modify")
    @ApiOperation("修改sip账号")
    public Result modify(@RequestBody SipBookVo sipBookVo) {
        sipBookService.update(sipBookVo);
        return Result.success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除sip账号")
    public Result delete(@RequestParam("sipBookId") Integer sipBookId){
        sipBookService.delete(sipBookId);
        return Result.success(true);
    }

    @GetMapping("/queryByAddressBookId")
    @ApiOperation("根据通讯录id查询sip账号信息")
    public Result queryByAddressBookId(@RequestParam("addressBookId") Integer addressBookId){
        return Result.success(sipBookService.querySipByBookId(addressBookId));
    }

    @GetMapping("/queryAll")
    @ApiOperation("查询sip账号")
    public Result queryAll(){
        return Result.success(sipBookService.queryAllSip());
    }
}
