package com.rikkeisoft.employee.controller;

import com.rikkeisoft.employee.repository.DepartmentRepository;
import com.rikkeisoft.employee.service.DepartmentService;
import com.rikkeisoft.employee.service.dto.DepartmentDTO;
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
public class DepartmentController {
    
    private final DepartmentService departmentService;

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentService departmentService, DepartmentRepository departmentRepository) {
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
    }

    @PostMapping("/department")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) throws URISyntaxException {
        if (departmentDTO.getId() != null) {
            return new ResponseEntity("A new department cannot already have an ID", HttpStatus.BAD_REQUEST);
        }

        DepartmentDTO result = departmentService.save(departmentDTO);
        return ResponseEntity
                .created(new URI("/api/department/" + result.getId()))
                .body(result);
    }

    @PutMapping("/department/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable final Long id, @RequestBody DepartmentDTO departmentDTO) {
        if (departmentDTO.getId() == null || !Objects.equals(id, departmentDTO.getId())) {
            return new ResponseEntity("Invalid ID", HttpStatus.BAD_REQUEST);
        }
        if (!departmentRepository.existsById(id)) {
            return new ResponseEntity("Entity not found", HttpStatus.BAD_REQUEST);
        }

        DepartmentDTO result = departmentService.save(departmentDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/department")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartment(Pageable pageable) {
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        return ResponseEntity.ok(page.getContent());
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id) {
        Optional<DepartmentDTO> departmentDTO = departmentService.findOne(id);
        if (departmentDTO.isPresent()) {
            return ResponseEntity.ok(departmentDTO.get());
        }
        return new ResponseEntity("Entity not found", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/department/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        if (!departmentRepository.existsById(id)) {
            return new ResponseEntity("Entity not found", HttpStatus.BAD_REQUEST);
        }
        departmentService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
