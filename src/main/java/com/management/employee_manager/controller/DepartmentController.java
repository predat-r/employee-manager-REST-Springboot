package com.management.employee_manager.controller;

import com.management.employee_manager.dto.DepartmentRequestDto;
import com.management.employee_manager.dto.DepartmentResponseDto;
import com.management.employee_manager.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDto> createDepartment(@RequestBody DepartmentRequestDto departmentRequestDto){

        DepartmentResponseDto departmentResponseDto = departmentService.createDepartment(departmentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentResponseDto);

    }
}
