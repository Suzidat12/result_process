package com.funaab.controller;

import com.funaab.dto.*;
import com.funaab.model.Student;
import com.funaab.service.CourseRegService;
import com.funaab.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StudentController {
    private final StudentService studentService;
    private final CourseRegService courseRegService;
    @GetMapping("/all")
    public ResponseEntity<List<Student>> all(){
        return studentService.getAllStudent();
    }
    @GetMapping("/matric-no")
    public ResponseEntity<List<Student>> getLevel(@RequestParam("matricNo")String matricNo){
        return studentService.getAllStudentByMaticNo(matricNo);
    }

    @PostMapping("/course-reg")
    public ResponseEntity<String> registerCourse(@RequestBody CourseRequest request){
        return courseRegService.registerCourse(request);
    }

    @GetMapping("/course-reg")
    public ResponseEntity<StudentResponse> getStudentWithCourses(@RequestParam("matricNo")String matricNo){
        return courseRegService.getStudentWithCourses(matricNo);
    }
}
