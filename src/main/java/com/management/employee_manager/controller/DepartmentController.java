package com.management.employee_manager.controller;

import com.management.employee_manager.dto.DepartmentRequestDto;
import com.management.employee_manager.dto.DepartmentResponseDto;
import com.management.employee_manager.service.DepartmentService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> getAllDepartments(){
        List<DepartmentResponseDto> departmentResponseDtos = departmentService.getAllDepartments();
        return ResponseEntity.ok().body(departmentResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable Long id) {
        DepartmentResponseDto departmentResponseDto =
                departmentService.getDepartmentById(id);

        return ResponseEntity.ok(departmentResponseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> updateDepartment(@PathVariable Long id,@RequestBody DepartmentRequestDto departmentRequestDto){
        DepartmentResponseDto departmentResponseDto = departmentService.updateDepartment(id,departmentRequestDto);
        return ResponseEntity.ok().body(departmentResponseDto);
    }
}
