package com.management.employee_manager.employee;

import com.management.employee_manager.common.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmployeeControllerTest {

    private EmployeeService employeeService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        employeeService = mock(EmployeeService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new EmployeeController(employeeService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createEmployeeReturnsCreatedEmployee() throws Exception {
        when(employeeService.createEmployee(any(EmployeeRequestDto.class))).thenReturn(responseDto());

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validEmployeeJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.firstName").value("Ali"))
                .andExpect(jsonPath("$.departmentName").value("Engineering"));
    }

    @Test
    void createEmployeeReturnsBadRequestForInvalidBody() throws Exception {
        EmployeeRequestDto requestDto = new EmployeeRequestDto(
                "",
                "Khan",
                "bad-email",
                "Backend Developer",
                new BigDecimal("-1.00"),
                null
        );

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "",
                                  "lastName": "Khan",
                                  "email": "bad-email",
                                  "jobTitle": "Backend Developer",
                                  "salary": -1.00,
                                  "departmentId": null
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.salary").exists())
                .andExpect(jsonPath("$.departmentId").exists());
    }

    @Test
    void getAllEmployeesReturnsEmployees() throws Exception {
        when(employeeService.getAllEmployees(0, 1)).thenReturn(new PageImpl<>(
                List.of(responseDto()),
                PageRequest.of(0, 1),
                1
        ));

        mockMvc.perform(get("/api/employees")
                        .param("page", "0")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Ali"))
                .andExpect(jsonPath("$.content[0].departmentName").value("Engineering"));
    }

    @Test
    void getEmployeeByIdReturnsEmployee() throws Exception {
        when(employeeService.getEmployeeById(10L)).thenReturn(responseDto());

        mockMvc.perform(get("/api/employees/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.email").value("ali.khan@example.com"));
    }

    @Test
    void updateEmployeeReturnsUpdatedEmployee() throws Exception {
        when(employeeService.updateEmployee(eq(10L), any(EmployeeRequestDto.class))).thenReturn(responseDto());

        mockMvc.perform(put("/api/employees/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validEmployeeJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ali"));
    }

    @Test
    void deleteEmployeeReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/employees/10"))
                .andExpect(status().isNoContent());

        verify(employeeService).deleteEmployee(10L);
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

    private String validEmployeeJson() {
        return """
                {
                  "firstName": "Ali",
                  "lastName": "Khan",
                  "email": "ali.khan@example.com",
                  "jobTitle": "Backend Developer",
                  "salary": 150000.00,
                  "departmentId": 1
                }
                """;
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
