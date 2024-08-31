package com.funaab.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LecturerRequest {
    private String staffNo;
    private String fullName;
    private String college;
    private String department;
    private String gender;
    private String email;
    private String password;
    private String confirmPassword;
}
