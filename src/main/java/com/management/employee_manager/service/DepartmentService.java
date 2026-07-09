package com.management.employee_manager.service;

import com.management.employee_manager.dto.DepartmentRequestDto;
import com.management.employee_manager.dto.DepartmentResponseDto;
import com.management.employee_manager.model.Department;
import com.management.employee_manager.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto){
        Department department = new Department(requestDto.getName(),requestDto.getDescription());
        Department savedDepartment = departmentRepository.save(department);

        return new DepartmentResponseDto(
                savedDepartment.getId(),
                savedDepartment.getName(),
                savedDepartment.getDescription()
        );
    }
    public List<DepartmentResponseDto> getAllDepartments(){
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponseDto> departmentResponseDtos = new ArrayList<>();
        for ( Department department : departments){
            departmentResponseDtos.add(new DepartmentResponseDto(department.getId(),department.getName(),department.getDescription()));
        }

        return departmentResponseDtos;

    }
}



