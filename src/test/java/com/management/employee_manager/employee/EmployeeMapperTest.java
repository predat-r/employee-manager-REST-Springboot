package com.management.employee_manager.employee;

import com.management.employee_manager.department.Department;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeMapperTest {

    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    @Test
    void toEntityMapsRequestDtoAndDepartmentToEmployee() {
        Department department = new Department("Engineering", "Software development");
        EmployeeRequestDto requestDto = new EmployeeRequestDto(
                "Ali",
                "Khan",
                "ali.khan@example.com",
                "Backend Developer",
                new BigDecimal("150000.00"),
                1L
        );

        Employee employee = employeeMapper.toEntity(requestDto, department);

        assertThat(employee.getFirstName()).isEqualTo("Ali");
        assertThat(employee.getLastName()).isEqualTo("Khan");
        assertThat(employee.getEmail()).isEqualTo("ali.khan@example.com");
        assertThat(employee.getJobTitle()).isEqualTo("Backend Developer");
        assertThat(employee.getSalary()).isEqualByComparingTo("150000.00");
        assertThat(employee.getDepartment()).isSameAs(department);
    }

    @Test
    void toResponseDtoMapsEmployeeAndDepartmentFields() {
        Department department = new Department("HR", "Human Resources");
        ReflectionTestUtils.setField(department, "id", 3L);

        Employee employee = new Employee(
                "Sara",
                "Ahmed",
                "sara.ahmed@example.com",
                "HR Manager",
                new BigDecimal("180000.00"),
                department
        );
        ReflectionTestUtils.setField(employee, "id", 7L);

        EmployeeResponseDto responseDto = employeeMapper.toResponseDto(employee);

        assertThat(responseDto.getId()).isEqualTo(7L);
        assertThat(responseDto.getFirstName()).isEqualTo("Sara");
        assertThat(responseDto.getLastName()).isEqualTo("Ahmed");
        assertThat(responseDto.getEmail()).isEqualTo("sara.ahmed@example.com");
        assertThat(responseDto.getJobTitle()).isEqualTo("HR Manager");
        assertThat(responseDto.getSalary()).isEqualByComparingTo("180000.00");
        assertThat(responseDto.getDepartmentId()).isEqualTo(3L);
        assertThat(responseDto.getDepartmentName()).isEqualTo("HR");
    }
}
