package com.management.employee_manager.employee;


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
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {

        EmployeeResponseDto employeeResponseDto = employeeService.createEmployee(employeeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponseDto);

    }

    @GetMapping
    public ResponseEntity<Page<EmployeeResponseDto>> getAllEmployees(@RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        Page<EmployeeResponseDto> employeeResponseDtos = employeeService.getAllEmployees(page, size);
        return ResponseEntity.ok().body(employeeResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDto employeeResponseDto = employeeService.getEmployeeById(id);

        return ResponseEntity.ok(employeeResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        EmployeeResponseDto employeeResponseDto = employeeService.updateEmployee(id, employeeRequestDto);
        return ResponseEntity.ok().body(employeeResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();

    }
}
