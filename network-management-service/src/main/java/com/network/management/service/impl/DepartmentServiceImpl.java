package com.network.management.service.impl;

import com.network.management.common.exception.Assert;
import com.network.management.common.exception.IllegalParamException;
import com.network.management.domain.dao.Department;
import com.network.management.domain.search.DepartmentSearch;
import com.network.management.domain.search.Page;
import com.network.management.domain.vo.DepartmentVo;
import com.network.management.mapper.DepartmentMapper;
import com.network.management.service.DepartmentService;
import com.network.management.service.converter.DepartmentVoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 部门服务实现类
 * @author yyc
 * @date 2021/2/28 16:31
 */
@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private DepartmentVoConverter departmentVoConverter;
    @Override
    public void add(DepartmentVo departmentVo) {
        Assert.notNull(departmentVo, "部门对象不能为空!");
        Department dept = departmentMapper.selectByDepartmentName(departmentVo.getDepartmentName());
        if(Objects.nonNull(dept)){
            throw new IllegalParamException("部门已存在!");
        }
        Department department = departmentVoConverter.reverseConvert(departmentVo);
        if(Objects.isNull(department)){
            throw new IllegalParamException("DepartmentVoConverter转换后的部门数据为空!");
        }
        departmentMapper.insert(department);
    }

    @Override
    public void update(DepartmentVo departmentVo) {
        Assert.notNull(departmentVo, "部门对象不能为空!");
        Department department = departmentVoConverter.reverseConvert(departmentVo);
        if(Objects.isNull(department)){
            throw new IllegalParamException("DepartmentVoConverter转换后的部门数据为空!");
        }
        departmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public void delete(Integer id) {
        Assert.notNull(id, "部门id不能为空!");
        departmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DepartmentVo get(Integer id) {
        Assert.notNull(id, "部门id不能为空!");
        return departmentVoConverter.convert(departmentMapper.selectByPrimaryKey(id));
    }

    @Override
    public List<DepartmentVo> queryAllDepartment() {
        return departmentVoConverter.convertToList(departmentMapper.queryAllDepartment());
    }

    @Override
    public Page<DepartmentVo> search(DepartmentSearch search) {
        Page<DepartmentVo> result = new Page<>();
        List<Department> departments = departmentMapper.search(search);
        result.setData(departmentVoConverter.convertToList(departments));
        result.setCount(departmentMapper.count(search));
        result.setPageSize(search.getPageSize());
        result.setCurrentPage(search.getCurrentPage());
        return result;
    }
}
