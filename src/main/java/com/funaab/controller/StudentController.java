package com.funaab.controller;

import com.funaab.dto.*;
import com.funaab.model.Course;
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

    @PostMapping("/add-rectification")
    public ResponseEntity<String> addRectification(@RequestBody RectificationRequest request){
        return studentService.addRectification(request);
    }



    @GetMapping("/course-reg")
    public ResponseEntity<StudentResponse> getStudentWithCourses(@RequestParam("matricNo")String matricNo){
        return courseRegService.getStudentWithCourses(matricNo);
    }

    @GetMapping("/view-result")
    public ResponseEntity<StudentResponse> getViewResult(@RequestParam("matricNo")String matricNo){
        return courseRegService.viewResult(matricNo);
    }

    @GetMapping("/all-course")
    public ResponseEntity<List<Course>> allCourse(@RequestParam("level")String level){
        return courseRegService.getAllCourses(level);
    }
}
