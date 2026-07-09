package com.management.employee_manager.department;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class DepartmentMapperTest {

    private final DepartmentMapper departmentMapper = new DepartmentMapper();

    @Test
    void toEntityMapsRequestDtoToDepartment() {
        DepartmentRequestDto requestDto = new DepartmentRequestDto("HR", "Human Resources");

        Department department = departmentMapper.toEntity(requestDto);

        assertThat(department.getName()).isEqualTo("HR");
        assertThat(department.getDescription()).isEqualTo("Human Resources");
    }

    @Test
    void toResponseDtoMapsDepartmentToResponseDto() {
        Department department = new Department("Engineering", "Software development");
        ReflectionTestUtils.setField(department, "id", 10L);

        DepartmentResponseDto responseDto = departmentMapper.toResponseDto(department);

        assertThat(responseDto.getId()).isEqualTo(10L);
        assertThat(responseDto.getName()).isEqualTo("Engineering");
        assertThat(responseDto.getDescription()).isEqualTo("Software development");
    }
}
