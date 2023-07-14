package com.rikkeisoft.employee.repository;

import com.rikkeisoft.employee.domain.Employee;
import com.rikkeisoft.employee.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> { }
