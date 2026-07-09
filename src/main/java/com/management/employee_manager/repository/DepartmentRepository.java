package com.management.employee_manager.repository;

import com.management.employee_manager.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface DepartmentRepository extends JpaRepository<Department,Long> {

}
