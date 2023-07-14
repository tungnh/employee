package com.rikkeisoft.employee.controller;

import com.rikkeisoft.employee.domain.Employee;
import com.rikkeisoft.employee.repository.EmployeeRepository;
import com.rikkeisoft.employee.service.EmployeeService;
import com.rikkeisoft.employee.service.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        if (employeeDTO.getId() != null) {
            return new ResponseEntity("A new employee cannot already have an ID", HttpStatus.BAD_REQUEST);
        }
        if (employeeRepository.findOneByEmailIgnoreCase(employeeDTO.getEmail()).isPresent()) {
            return new ResponseEntity("Email is already in use!", HttpStatus.BAD_REQUEST);
        }

        EmployeeDTO result = employeeService.save(employeeDTO);
        return ResponseEntity
                .created(new URI("/api/employee/" + result.getId()))
                .body(result);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable final Long id, @RequestBody EmployeeDTO employeeDTO) {
        if (employeeDTO.getId() == null || !Objects.equals(id, employeeDTO.getId())) {
            return new ResponseEntity("Invalid ID", HttpStatus.BAD_REQUEST);
        }
        if (!employeeRepository.existsById(id)) {
            return new ResponseEntity("Entity not found", HttpStatus.BAD_REQUEST);
        }
        Optional<Employee> existingUser = employeeRepository.findOneByEmailIgnoreCase(employeeDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(employeeDTO.getId()))) {
            return new ResponseEntity("Email is already in use!", HttpStatus.BAD_REQUEST);
        }

        EmployeeDTO result = employeeService.save(employeeDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/employee")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(
            @RequestParam(value = "textSearch", required = false, defaultValue = "") String textSearch,
            Pageable pageable) {
        Page<EmployeeDTO> page = employeeService.findAll(textSearch, pageable);
        return ResponseEntity.ok(page.getContent());
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        Optional<EmployeeDTO> employeeDTO = employeeService.findOne(id);
        if (employeeDTO.isPresent()) {
            return ResponseEntity.ok(employeeDTO.get());
        }
        return new ResponseEntity("Entity not found", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (!employeeRepository.existsById(id)) {
            return new ResponseEntity("Entity not found", HttpStatus.BAD_REQUEST);
        }
        employeeService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
