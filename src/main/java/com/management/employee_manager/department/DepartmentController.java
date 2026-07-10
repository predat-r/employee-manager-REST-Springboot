package com.management.employee_manager.department;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Validated
public class DepartmentController {
    private final DepartmentService departmentService;


    @PostMapping
    public ResponseEntity<DepartmentResponseDto> createDepartment(@Valid @RequestBody DepartmentRequestDto departmentRequestDto) {

        DepartmentResponseDto departmentResponseDto = departmentService.createDepartment(departmentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentResponseDto);

    }

    @GetMapping
    public ResponseEntity<Page<DepartmentResponseDto>> getAllDepartments(@RequestParam @Min(0) int page, @RequestParam @Min(1) @Max(100) int size) {
        Page<DepartmentResponseDto> departmentResponseDtos = departmentService.getAllDepartments(page,size);
        return ResponseEntity.ok().body(departmentResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable Long id) {
        DepartmentResponseDto departmentResponseDto =
                departmentService.getDepartmentById(id);

        return ResponseEntity.ok(departmentResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequestDto departmentRequestDto) {
        DepartmentResponseDto departmentResponseDto = departmentService.updateDepartment(id, departmentRequestDto);
        return ResponseEntity.ok().body(departmentResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
