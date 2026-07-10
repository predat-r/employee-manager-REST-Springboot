package com.management.employee_manager.department;

import com.management.employee_manager.common.exception.DuplicateResourceException;
import com.management.employee_manager.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;


    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {
        if (departmentRepository.existsByNameIgnoreCase(requestDto.getName())) {
            throw new DuplicateResourceException("Department with this name already exists");
        }
        Department department = departmentMapper.toEntity(requestDto);
        Department savedDepartment = departmentRepository.save(department);

        return departmentMapper.toResponseDto(savedDepartment);
    }

    public Page<DepartmentResponseDto> getAllDepartments(int page, int size) {
        Page<Department> departments = departmentRepository.findAll(PageRequest.of(page, size));
        return departments.map(departmentMapper::toResponseDto);

    }


    public DepartmentResponseDto getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return departmentMapper.toResponseDto(department);


    }

    public DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto requestDto) {

        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        if (departmentRepository.existsByNameIgnoreCaseAndIdNot(requestDto.getName(), id)) {
            throw new DuplicateResourceException("Department with this name already exists");
        }
        department.setDescription(requestDto.getDescription());
        department.setName(requestDto.getName());
        Department updatedDepartment = departmentRepository.save(department);
        return departmentMapper.toResponseDto(updatedDepartment);
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        departmentRepository.delete(department);
    }


}


