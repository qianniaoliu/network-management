package com.network.management.mapper;

import com.network.management.domain.dao.Department;
import com.network.management.domain.search.DepartmentSearch;

import java.util.List;


/**
 * 部门mapper
 * @author yyc
 * @date 2021/2/27 13:21
 */
public interface DepartmentMapper {
    /**
     * 插入部门信息
     * @param department {@link Department}
     * @return 返回主键id
     */
    Integer insert(Department department);

    /**
     * 修改部门数据
     * @param department 部门信息
     */
    void updateByPrimaryKeySelective(Department department);

    /**
     * 通过主键id删除部门信息
     * @param id 主键id
     */
    void deleteByPrimaryKey(Integer id);

    /**
     * 条件查询总数
     * @param depSearch 搜索条件
     * @return 总数
     */
    Integer count(DepartmentSearch depSearch);

    /**
     * 条件搜索
     * @param depSearch 搜索条件
     * @return 部门列表
     */
    List<Department> search(DepartmentSearch depSearch);

    /**
     * 根据部门id获取部门信息
     * @param id 部门id
     * @return 部门信息
     */
    Department selectByPrimaryKey(Integer id);

    /**
     * 根据名称查询部门
     * @param departmentName 部门名称
     * @return {@link Department}
     */
    Department selectByDepartmentName(String departmentName);

    /**
     * 查询所有部门
     * @return {@link List<Department>}
     */
    List<Department> queryAllDepartment();
}
