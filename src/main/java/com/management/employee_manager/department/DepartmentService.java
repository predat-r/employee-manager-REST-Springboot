package com.management.employee_manager.department;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;



    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {
        Department department = new Department(requestDto.getName(), requestDto.getDescription());
        Department savedDepartment = departmentRepository.save(department);

        return new DepartmentResponseDto(
                savedDepartment.getId(),
                savedDepartment.getName(),
                savedDepartment.getDescription()
        );
    }

    public List<DepartmentResponseDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponseDto> departmentResponseDtos = new ArrayList<>();
        for (Department department : departments) {
            departmentResponseDtos.add(new DepartmentResponseDto(department.getId(), department.getName(), department.getDescription()));
        }

        return departmentResponseDtos;

    }


    public DepartmentResponseDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow();
        return new DepartmentResponseDto(
                department.getId(),
                department.getName(),
                department.getDescription()
        );


    }
    public DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto requestDto){
        Department department = departmentRepository.findById(id).orElseThrow();
        department.setDescription(requestDto.getDescription());
        department.setName(requestDto.getName());
        Department updatedDepartment = departmentRepository.save(department);
        return new DepartmentResponseDto(updatedDepartment.getId(),updatedDepartment.getName(),updatedDepartment.getDescription());
    }

    public void deleteDepartment(Long id){
        Department department = departmentRepository.findById(id).orElseThrow();
        departmentRepository.delete(department);
    }


}



