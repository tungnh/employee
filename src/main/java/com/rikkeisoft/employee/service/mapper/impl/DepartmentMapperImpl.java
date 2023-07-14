package com.rikkeisoft.employee.service.mapper.impl;

import com.rikkeisoft.employee.domain.Department;
import com.rikkeisoft.employee.service.dto.DepartmentDTO;
import com.rikkeisoft.employee.service.mapper.DepartmentMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentMapperImpl implements DepartmentMapper {
    @Override
    public Department toEntity(DepartmentDTO dto) {
        if (dto == null) {
            return null;
        }

        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        return department;
    }

    @Override
    public DepartmentDTO toDto(Department entity) {
        if (entity == null) {
            return null;
        }

        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(entity.getId());
        departmentDTO.setName(entity.getName());
        departmentDTO.setDescription(entity.getDescription());
        return departmentDTO;
    }

    @Override
    public List<Department> toEntity(List<DepartmentDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Department> list = new ArrayList<Department>(dtoList.size());
        for (DepartmentDTO departmentDTO : dtoList) {
            list.add(toEntity(departmentDTO));
        }

        return list;
    }

    @Override
    public List<DepartmentDTO> toDto(List<Department> entityList) {
        if (entityList == null) {
            return null;
        }

        List<DepartmentDTO> list = new ArrayList<DepartmentDTO>(entityList.size());
        for (Department department : entityList) {
            list.add(toDto(department));
        }

        return list;
    }
}
