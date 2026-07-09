package com.management.employee_manager.employee;

import com.management.employee_manager.department.Department;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequestDto requestDto, Department department) {
        return new Employee(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getEmail(),
                requestDto.getJobTitle(),
                requestDto.getSalary(),
                department
        );
    }

    public EmployeeResponseDto toResponseDto(Employee employee) {
        return new EmployeeResponseDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getJobTitle(),
                employee.getSalary(),
                employee.getDepartment().getId(),
                employee.getDepartment().getName()
        );
    }
}
