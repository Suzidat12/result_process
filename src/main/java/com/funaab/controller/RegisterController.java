package com.funaab.controller;

import com.funaab.dto.JwtAuthenticationResponse;
import com.funaab.dto.LoginRequest;
import com.funaab.dto.StudentRequest;
import com.funaab.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RegisterController {
    private final StudentService studentService;
    @PostMapping("/")
    public ResponseEntity<String> register(@RequestBody StudentRequest request){
        return studentService.registerStudent(request);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(studentService.login(request));
    }
}
