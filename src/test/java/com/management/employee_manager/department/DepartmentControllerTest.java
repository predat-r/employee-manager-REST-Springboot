package com.management.employee_manager.department;

import com.management.employee_manager.common.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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

class DepartmentControllerTest {

    private DepartmentService departmentService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        departmentService = mock(DepartmentService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new DepartmentController(departmentService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createDepartmentReturnsCreatedDepartment() throws Exception {
        DepartmentRequestDto requestDto = new DepartmentRequestDto("HR", "Human Resources");
        DepartmentResponseDto responseDto = new DepartmentResponseDto(1L, "HR", "Human Resources");

        when(departmentService.createDepartment(any(DepartmentRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "HR",
                                  "description": "Human Resources"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("HR"))
                .andExpect(jsonPath("$.description").value("Human Resources"));
    }

    @Test
    void createDepartmentReturnsBadRequestForInvalidBody() throws Exception {
        DepartmentRequestDto requestDto = new DepartmentRequestDto("", "Human Resources");

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "",
                                  "description": "Human Resources"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    void getAllDepartmentsReturnsDepartments() throws Exception {
        when(departmentService.getAllDepartments(0, 2)).thenReturn(new PageImpl<>(List.of(
                new DepartmentResponseDto(1L, "HR", "Human Resources"),
                new DepartmentResponseDto(2L, "Engineering", "Software development")
        ), PageRequest.of(0, 2), 2));

        mockMvc.perform(get("/api/departments")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("HR"))
                .andExpect(jsonPath("$.content[1].name").value("Engineering"));
    }

    @Test
    void getDepartmentByIdReturnsDepartment() throws Exception {
        when(departmentService.getDepartmentById(1L))
                .thenReturn(new DepartmentResponseDto(1L, "HR", "Human Resources"));

        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("HR"));
    }

    @Test
    void updateDepartmentReturnsUpdatedDepartment() throws Exception {
        DepartmentRequestDto requestDto = new DepartmentRequestDto("People Ops", "Employee lifecycle");
        DepartmentResponseDto responseDto = new DepartmentResponseDto(1L, "People Ops", "Employee lifecycle");

        when(departmentService.updateDepartment(eq(1L), any(DepartmentRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "People Ops",
                                  "description": "Employee lifecycle"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("People Ops"));
    }

    @Test
    void deleteDepartmentReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isNoContent());

        verify(departmentService).deleteDepartment(1L);
    }
}
