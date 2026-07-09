package com.management.employee_manager.model;

import jakarta.persistence.*;

@Entity
@Table(name="departments")
public class Department {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 private String name;
 private String description;


    public Department() {
    }

    public Department(String name, String description) {

        this.name = name;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }
}
