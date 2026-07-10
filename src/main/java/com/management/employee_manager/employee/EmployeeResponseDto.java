package com.management.employee_manager.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {

    private Long id;
    private String firstName;


    private String lastName;


    private String email;

    private String jobTitle;


    private BigDecimal salary;
    private Long departmentId;
    private String departmentName;

}
