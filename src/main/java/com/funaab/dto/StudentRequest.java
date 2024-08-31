package com.funaab.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class StudentRequest {
    private String matricNo;
    private String fullName;
    private String college;
    private String department;
    private String gender;
    private String level;
    private String email;
    private String password;
    private String confirmPassword;
}
