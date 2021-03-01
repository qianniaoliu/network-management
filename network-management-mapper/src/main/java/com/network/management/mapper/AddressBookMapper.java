package com.network.management.mapper;

import com.network.management.domain.dao.AddressBook;

import java.util.List;

/**
 * 通讯录mapper
 * @author yyc
 * @date 2021/2/27 16:37
 */
public interface AddressBookMapper {

    /**
     * 插入通讯录信息
     * @param addressBook {@link AddressBook}
     * @return 返回主键id
     */
    Integer insert(AddressBook addressBook);

    /**
     * 修改通讯录信息
     * @param addressBook 通讯录信息
     */
    void updateByPrimaryKeySelective(AddressBook addressBook);

    /**
     * 通过主键id删除通讯录信息
     * @param id 主键id
     */
    void deleteByPrimaryKey(Integer id);

    /**
     * 查询所有通讯录信息
     * @return {@link List<AddressBook>}
     */
    List<AddressBook> queryAllAddressBooks();

    /**
     * 通过部门id查询通讯录
     * @param departmentId 部门id
     * @return {@link AddressBook}
     */
    AddressBook queryAddressBookByDepartmentId(Integer departmentId);
}
