package com.network.management.service;

import com.network.management.domain.dao.Department;
import com.network.management.domain.search.DepartmentSearch;
import com.network.management.domain.search.Page;
import com.network.management.domain.vo.DepartmentVo;

import java.util.List;

/**
 * 部门服务类
 * @author yyc
 * @date 2021/2/28 11:50
 */
public interface DepartmentService {
    /**
     * 新增部门信息
     *
     * @param departmentVo 部门信息
     * @return 部门id
     */
    void add(DepartmentVo departmentVo);

    /**
     * 修改部门信息
     *
     * @param departmentVo 部门信息
     */
    void update(DepartmentVo departmentVo);

    /**
     * 删除部门信息
     *
     * @param id 部门id
     */
    void delete(Integer id);

    /**
     * 获取单个部门信息
     *
     * @param id 部门id
     * @return 部门信息
     */
    DepartmentVo get(Integer id);

    /**
     * 查询所有部门信息
     * @return {@link List<Department>}
     */
    List<DepartmentVo> queryAllDepartment();

    /**
     * 分页查询部门信息
     * @param search 条件对象
     * @return 分页数据
     */
    Page<DepartmentVo> search(DepartmentSearch search);

}
