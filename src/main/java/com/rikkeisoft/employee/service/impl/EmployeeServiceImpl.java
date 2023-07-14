package com.rikkeisoft.employee.service.impl;

import com.rikkeisoft.employee.domain.Employee;
import com.rikkeisoft.employee.domain.Role;
import com.rikkeisoft.employee.repository.EmployeeRepository;
import com.rikkeisoft.employee.repository.RoleRepository;
import com.rikkeisoft.employee.service.EmployeeService;
import com.rikkeisoft.employee.service.dto.EmployeeDTO;
import com.rikkeisoft.employee.service.mapper.EmployeeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final RoleRepository roleRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RoleRepository roleRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        if (employeeDTO.getRoles() != null) {
            Set<Role> roles = employeeDTO
                    .getRoles()
                    .stream()
                    .map(roleRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            employee.setRoles(roles);
        }
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    public Page<EmployeeDTO> findAll(String textSearch, Pageable pageable) {
        return employeeRepository.findAllByNameOrEmailContainingIgnoreCase(textSearch, textSearch, pageable).map(employeeMapper::toDto);
    }

    @Override
    public Optional<EmployeeDTO> findOne(Long id) {
        return employeeRepository.findById(id).map(employeeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
