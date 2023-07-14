package com.rikkeisoft.employee.service;

import com.rikkeisoft.employee.service.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EmployeeService {

    EmployeeDTO save(EmployeeDTO notifyDTO);

    Page<EmployeeDTO> findAll(String textSearch, Pageable pageable);

    Optional<EmployeeDTO> findOne(Long id);

    void delete(Long id);
}
