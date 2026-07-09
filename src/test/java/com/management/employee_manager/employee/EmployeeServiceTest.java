package com.management.employee_manager.employee;

import com.management.employee_manager.department.Department;
import com.management.employee_manager.department.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void createEmployeeFindsDepartmentSavesEmployeeAndReturnsResponseDto() {
        Department department = new Department("Engineering", "Software development");
        EmployeeRequestDto requestDto = requestDto();
        Employee employee = employee(department);
        Employee savedEmployee = employee(department);
        EmployeeResponseDto responseDto = responseDto();

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(employeeMapper.toEntity(requestDto, department)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(savedEmployee);
        when(employeeMapper.toResponseDto(savedEmployee)).thenReturn(responseDto);

        EmployeeResponseDto result = employeeService.createEmployee(requestDto);

        assertThat(result).isSameAs(responseDto);
        verify(employeeRepository).save(employee);
    }

    @Test
    void createEmployeeThrowsWhenDepartmentIsMissing() {
        EmployeeRequestDto requestDto = requestDto();
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.createEmployee(requestDto))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void getAllEmployeesReturnsMappedDtos() {
        Department department = new Department("Engineering", "Software development");
        Employee employee = employee(department);
        EmployeeResponseDto responseDto = responseDto();

        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(employeeMapper.toResponseDto(employee)).thenReturn(responseDto);

        List<EmployeeResponseDto> result = employeeService.getAllEmployees();

        assertThat(result).containsExactly(responseDto);
    }

    @Test
    void getEmployeeByIdReturnsMappedDtoWhenFound() {
        Department department = new Department("Engineering", "Software development");
        Employee employee = employee(department);
        EmployeeResponseDto responseDto = responseDto();

        when(employeeRepository.findById(10L)).thenReturn(Optional.of(employee));
        when(employeeMapper.toResponseDto(employee)).thenReturn(responseDto);

        EmployeeResponseDto result = employeeService.getEmployeeById(10L);

        assertThat(result).isSameAs(responseDto);
    }

    @Test
    void updateEmployeeUpdatesFieldsDepartmentAndReturnsMappedDto() {
        Department oldDepartment = new Department("HR", "Human Resources");
        Department newDepartment = new Department("Engineering", "Software development");
        Employee employee = employee(oldDepartment);
        EmployeeRequestDto requestDto = requestDto();
        EmployeeResponseDto responseDto = responseDto();

        when(employeeRepository.findById(10L)).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(newDepartment));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toResponseDto(employee)).thenReturn(responseDto);

        EmployeeResponseDto result = employeeService.updateEmployee(10L, requestDto);

        assertThat(employee.getFirstName()).isEqualTo("Ali");
        assertThat(employee.getLastName()).isEqualTo("Khan");
        assertThat(employee.getEmail()).isEqualTo("ali.khan@example.com");
        assertThat(employee.getJobTitle()).isEqualTo("Backend Developer");
        assertThat(employee.getSalary()).isEqualByComparingTo("150000.00");
        assertThat(employee.getDepartment()).isSameAs(newDepartment);
        assertThat(result).isSameAs(responseDto);
    }

    @Test
    void deleteEmployeeDeletesFoundEmployee() {
        Department department = new Department("Engineering", "Software development");
        Employee employee = employee(department);
        when(employeeRepository.findById(10L)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(10L);

        verify(employeeRepository).delete(employee);
    }

    private EmployeeRequestDto requestDto() {
        return new EmployeeRequestDto(
                "Ali",
                "Khan",
                "ali.khan@example.com",
                "Backend Developer",
                new BigDecimal("150000.00"),
                1L
        );
    }

    private Employee employee(Department department) {
        return new Employee(
                "Ali",
                "Khan",
                "ali.khan@example.com",
                "Backend Developer",
                new BigDecimal("150000.00"),
                department
        );
    }

    private EmployeeResponseDto responseDto() {
        return new EmployeeResponseDto(
                10L,
                "Ali",
                "Khan",
                "ali.khan@example.com",
                "Backend Developer",
                new BigDecimal("150000.00"),
                1L,
                "Engineering"
        );
    }
}
