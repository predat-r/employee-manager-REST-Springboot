package com.management.employee_manager.dto;

public class DepartmentResponseDto
{
    private Long id;
    private String name;
    private String description;

    public DepartmentResponseDto(Long id,String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public DepartmentResponseDto() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
