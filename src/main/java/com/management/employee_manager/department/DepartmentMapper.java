package com.management.employee_manager.department;

import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentRequestDto requestDto) {
        return new Department(
                requestDto.getName(),
                requestDto.getDescription()
        );
    }

    public DepartmentResponseDto toResponseDto(Department department) {
        return new DepartmentResponseDto(
                department.getId(),
                department.getName(),
                department.getDescription()
        );
    }
}
