package com.network.management.service.converter;

import com.network.management.common.convert.Converter;
import com.network.management.domain.dao.Department;
import com.network.management.domain.enums.YnEnum;
import com.network.management.domain.vo.DepartmentVo;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * DepartmentVo对象转换类
 * @author yyc
 * @date 2021/3/1 15:28
 */
@Component
public class DepartmentVoConverter implements Converter<Department, DepartmentVo> {
    @Override
    public DepartmentVo convert(Department department) {
        if(Objects.nonNull(department)){
            DepartmentVo departmentVo = new DepartmentVo();
            departmentVo.setId(department.getId());
            departmentVo.setDepartmentName(department.getDepartmentName());
            departmentVo.setParentId(department.getParentId());
            departmentVo.setCreated(department.getCreated());
            departmentVo.setModified(department.getModified());
            return departmentVo;
        }
        return null;
    }

    @Override
    public Department reverseConvert(DepartmentVo departmentVo) {
        if(Objects.nonNull(departmentVo)){
            Department department = new Department();
            department.setId(departmentVo.getId());
            department.setDepartmentName(departmentVo.getDepartmentName());
            department.setParentId(departmentVo.getParentId());
            if(Objects.isNull(departmentVo.getId())){
                department.setYn(YnEnum.YES.getCode());
                department.setCreated(new Date());
            }
            department.setModified(new Date());
            return department;
        }
        return null;
    }
}
