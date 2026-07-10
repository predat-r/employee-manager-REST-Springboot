package com.management.employee_manager.department;

import com.management.employee_manager.common.exception.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<DepartmentResponseDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponseDto> departmentResponseDtos = new ArrayList<>();
        for (Department department : departments) {
            departmentResponseDtos.add(departmentMapper.toResponseDto(department));
        }

        return departmentResponseDtos;

    }


    public DepartmentResponseDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow();
        return departmentMapper.toResponseDto(department);


    }
    public DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto requestDto){
        Department department = departmentRepository.findById(id).orElseThrow();
        department.setDescription(requestDto.getDescription());
        department.setName(requestDto.getName());
        Department updatedDepartment = departmentRepository.save(department);
        return departmentMapper.toResponseDto(updatedDepartment);
    }

    public void deleteDepartment(Long id){
        Department department = departmentRepository.findById(id).orElseThrow();
        departmentRepository.delete(department);
    }


}


