package com.management.employee_manager.department;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDto
{
    private Long id;
    @Setter
    private String name;
    @Setter
    private String description;

}
