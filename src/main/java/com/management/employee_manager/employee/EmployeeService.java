package com.management.employee_manager.employee;

import com.management.employee_manager.common.exception.DuplicateResourceException;
import com.management.employee_manager.common.exception.ResourceNotFoundException;
import com.management.employee_manager.department.Department;
import com.management.employee_manager.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) {
        if (employeeRepository.existsByEmail(employeeRequestDto.getEmail())) {
            throw new DuplicateResourceException("Employee with this email already exists");
        }
        Department department = departmentRepository.findById(employeeRequestDto.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        Employee employee = employeeMapper.toEntity(employeeRequestDto, department);
        Employee createdEmployee = employeeRepository.save(employee);
        return employeeMapper.toResponseDto(createdEmployee);

    }

    @Transactional(readOnly = true)
    public Page<EmployeeResponseDto> getAllEmployees(int page, int size) {
        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page,size));

        return employees.map(employeeMapper::toResponseDto);
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return employeeMapper.toResponseDto(employee);
    }

    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto employeeRequestDto) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        Department department = departmentRepository.findById(employeeRequestDto.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        employee.setFirstName(employeeRequestDto.getFirstName());
        employee.setLastName(employeeRequestDto.getLastName());
        employee.setEmail(employeeRequestDto.getEmail());
        employee.setJobTitle(employeeRequestDto.getJobTitle());
        employee.setSalary(employeeRequestDto.getSalary());
        employee.setDepartment(department);

        Employee updatedEmployee = employeeRepository.save(employee);
        return employeeMapper.toResponseDto(updatedEmployee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        employeeRepository.delete(employee);
    }

}
