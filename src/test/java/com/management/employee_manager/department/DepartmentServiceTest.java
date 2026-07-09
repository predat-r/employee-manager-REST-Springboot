package com.management.employee_manager.department;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void createDepartmentSavesDepartmentAndReturnsResponseDto() {
        DepartmentRequestDto requestDto = new DepartmentRequestDto("HR", "Human Resources");
        Department department = new Department("HR", "Human Resources");
        Department savedDepartment = new Department("HR", "Human Resources");
        ReflectionTestUtils.setField(savedDepartment, "id", 1L);
        DepartmentResponseDto responseDto = new DepartmentResponseDto(1L, "HR", "Human Resources");

        when(departmentMapper.toEntity(requestDto)).thenReturn(department);
        when(departmentRepository.save(department)).thenReturn(savedDepartment);
        when(departmentMapper.toResponseDto(savedDepartment)).thenReturn(responseDto);

        DepartmentResponseDto result = departmentService.createDepartment(requestDto);

        assertThat(result).isSameAs(responseDto);
        verify(departmentRepository).save(department);
    }

    @Test
    void getAllDepartmentsReturnsMappedDtos() {
        Department hr = new Department("HR", "Human Resources");
        Department engineering = new Department("Engineering", "Software development");
        DepartmentResponseDto hrDto = new DepartmentResponseDto(1L, "HR", "Human Resources");
        DepartmentResponseDto engineeringDto = new DepartmentResponseDto(2L, "Engineering", "Software development");

        when(departmentRepository.findAll()).thenReturn(List.of(hr, engineering));
        when(departmentMapper.toResponseDto(hr)).thenReturn(hrDto);
        when(departmentMapper.toResponseDto(engineering)).thenReturn(engineeringDto);

        List<DepartmentResponseDto> result = departmentService.getAllDepartments();

        assertThat(result).containsExactly(hrDto, engineeringDto);
    }

    @Test
    void getDepartmentByIdReturnsMappedDtoWhenFound() {
        Department department = new Department("Finance", "Finance department");
        DepartmentResponseDto responseDto = new DepartmentResponseDto(4L, "Finance", "Finance department");

        when(departmentRepository.findById(4L)).thenReturn(Optional.of(department));
        when(departmentMapper.toResponseDto(department)).thenReturn(responseDto);

        DepartmentResponseDto result = departmentService.getDepartmentById(4L);

        assertThat(result).isSameAs(responseDto);
    }

    @Test
    void getDepartmentByIdThrowsWhenMissing() {
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> departmentService.getDepartmentById(99L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateDepartmentUpdatesFieldsAndReturnsMappedDto() {
        Department department = new Department("Old", "Old description");
        DepartmentRequestDto requestDto = new DepartmentRequestDto("New", "New description");
        DepartmentResponseDto responseDto = new DepartmentResponseDto(5L, "New", "New description");

        when(departmentRepository.findById(5L)).thenReturn(Optional.of(department));
        when(departmentRepository.save(department)).thenReturn(department);
        when(departmentMapper.toResponseDto(department)).thenReturn(responseDto);

        DepartmentResponseDto result = departmentService.updateDepartment(5L, requestDto);

        assertThat(department.getName()).isEqualTo("New");
        assertThat(department.getDescription()).isEqualTo("New description");
        assertThat(result).isSameAs(responseDto);
    }

    @Test
    void deleteDepartmentDeletesFoundDepartment() {
        Department department = new Department("HR", "Human Resources");
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        departmentService.deleteDepartment(1L);

        verify(departmentRepository).delete(department);
    }
}
