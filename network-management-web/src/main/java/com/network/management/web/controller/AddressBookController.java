package com.network.management.web.controller;

import com.network.management.domain.vo.AddressBookVo;
import com.network.management.service.AddressBookService;
import com.network.management.web.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 通讯录管理controller
 * @author yyc
 * @date 2021/03/01 13:50
 */
@RestController
@Api(tags = "通讯录管理")
@RequestMapping("/addressBook")
public class AddressBookController {
    @Resource
    private AddressBookService addressBookService;

    /**
     * 新增通讯录信息
     *
     * @param addressBookVo 通讯录信息
     * @return 返回结果
     */
    @PostMapping("/add")
    @ApiOperation("新增通讯录信息")
    public Result add(@RequestBody AddressBookVo addressBookVo) {
        addressBookService.add(addressBookVo);
        return Result.success(true);
    }

    /**
     * 修改通讯录信息
     *
     * @param addressBookVo 通讯录信息
     * @return 返回结果
     */
    @PostMapping("/modify")
    @ApiOperation("修改通讯录信息")
    public Result modify(@RequestBody AddressBookVo addressBookVo) {
        addressBookService.update(addressBookVo);
        return Result.success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除通讯录信息")
    public Result delete(@RequestParam("bookId") Integer bookId){
        addressBookService.delete(bookId);
        return Result.success(true);
    }

    @GetMapping("/queryDepartmentId")
    @ApiOperation("根据部门id查询通讯录信息")
    public Result queryAddressBookByDepartmentId(@RequestParam("departmentId") Integer departmentId){
        return Result.success(addressBookService.queryAddressBookByDepartmentId(departmentId));
    }

    @GetMapping("/queryAll")
    @ApiOperation("查询所有通讯录信息")
    public Result queryAll(){
        return Result.success(addressBookService.queryAllAddressBook());
    }
}
