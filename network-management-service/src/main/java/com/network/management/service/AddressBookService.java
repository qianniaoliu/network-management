package com.network.management.service;

import com.network.management.domain.vo.AddressBookVo;

import java.util.List;

/**
 * 通讯录服务类
 * @author yyc
 * @date 2021/2/28 11:50
 */
public interface AddressBookService {
    /**
     * 新增通讯录
     *
     * @param addressBookVo 通讯录信息
     * @return 通讯录id
     */
    void add(AddressBookVo addressBookVo);

    /**
     * 修改通讯录信息
     *
     * @param addressBookVo 通讯录信息
     */
    void update(AddressBookVo addressBookVo);

    /**
     * 删除通讯录信息
     *
     * @param id 通讯录id
     */
    void delete(Integer id);

    /**
     * 查询所有通讯录信息
     * @return {@link List<AddressBookVo>}
     */
    List<AddressBookVo> queryAllAddressBook();

    /**
     * 根据部门id查询通讯录
     * @param departmentId 部门id
     * @return {@link AddressBookVo}
     */
    AddressBookVo queryAddressBookByDepartmentId(Integer departmentId);
}
