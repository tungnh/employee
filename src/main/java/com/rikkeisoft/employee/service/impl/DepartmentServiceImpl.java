package com.rikkeisoft.employee.service.impl;

import com.rikkeisoft.employee.domain.Department;
import com.rikkeisoft.employee.domain.Employee;
import com.rikkeisoft.employee.repository.DepartmentRepository;
import com.rikkeisoft.employee.service.DepartmentService;
import com.rikkeisoft.employee.service.dto.DepartmentDTO;
import com.rikkeisoft.employee.service.dto.EmployeeDTO;
import com.rikkeisoft.employee.service.mapper.DepartmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        Department department = departmentMapper.toEntity(departmentDTO);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    @Override
    public Page<DepartmentDTO> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable).map(departmentMapper::toDto);
    }

    @Override
    public Optional<DepartmentDTO> findOne(Long id) {
        return departmentRepository.findById(id).map(departmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }
}
