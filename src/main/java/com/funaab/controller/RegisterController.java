package com.funaab.controller;

import com.funaab.dto.*;
import com.funaab.model.Course;
import com.funaab.service.CourseRegService;
import com.funaab.service.HodService;
import com.funaab.service.LecturerService;
import com.funaab.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RegisterController {
    private final StudentService studentService;
    private final CourseRegService courseRegService;
    private final LecturerService lecturerService;
    private final HodService hodService;
    @PostMapping("/")
    public ResponseEntity<String> register(@RequestBody StudentRequest request){
        return studentService.registerStudent(request);
    }


    @PostMapping("/lecturer")
    public ResponseEntity<String> registerLecturer(@RequestBody LecturerRequest request){
        return lecturerService.registerLecturer(request);
    }

    @PostMapping("/admin")
    public ResponseEntity<String> registerAdmin(@RequestBody LecturerRequest request){
        return hodService.registerAdmin(request);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(studentService.login(request));
    }

    @PostMapping("/login/lecturer")
    public ResponseEntity<JwtAuthenticationResponse> loginLecturer(@RequestBody LoginRequest request){
        return ResponseEntity.ok(lecturerService.login(request));
    }

    @PostMapping("/login/admin")
    public ResponseEntity<JwtAuthenticationResponse> loginAdmin(@RequestBody LoginRequest request){
        return ResponseEntity.ok(hodService.login(request));
    }
    @GetMapping("/course-list")
    public ResponseEntity<List<CourseRespose>> getCourses(){
        return courseRegService.getCourses();
    }


}
