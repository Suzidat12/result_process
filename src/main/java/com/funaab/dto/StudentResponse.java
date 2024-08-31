package com.funaab.dto;

import lombok.Data;

import java.util.Set;

@Data
public class StudentResponse {
    private Long id;
    private String matricNo;
    private String fullName;
    private String email;
    private String college;
    private String role;
    private String department;
    private String level;
    private String status;
    private String gender;
    private Set<CourseRegResponse> registrations;
}
