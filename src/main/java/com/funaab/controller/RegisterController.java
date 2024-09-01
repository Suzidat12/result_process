package com.funaab.controller;

import com.funaab.dto.JwtAuthenticationResponse;
import com.funaab.dto.LecturerRequest;
import com.funaab.dto.LoginRequest;
import com.funaab.dto.StudentRequest;
import com.funaab.service.LecturerService;
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
    private final LecturerService lecturerService;
    @PostMapping("/")
    public ResponseEntity<String> register(@RequestBody StudentRequest request){
        return studentService.registerStudent(request);
    }


    @PostMapping("/lecturer")
    public ResponseEntity<String> registerLecturer(@RequestBody LecturerRequest request){
        return lecturerService.registerLecturer(request);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(studentService.login(request));
    }

    @PostMapping("/login/lecturer")
    public ResponseEntity<JwtAuthenticationResponse> loginLecturer(@RequestBody LoginRequest request){
        return ResponseEntity.ok(lecturerService.login(request));
    }
}
