package com.funaab.dto;

import com.funaab.model.Student;
import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
    private Student student;
}
