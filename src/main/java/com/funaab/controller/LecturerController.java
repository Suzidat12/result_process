package com.funaab.controller;

import com.funaab.dto.CourseRequest;
import com.funaab.dto.CourseUpdateRequest;
import com.funaab.dto.StudentResponse;
import com.funaab.model.Course;
import com.funaab.model.Student;
import com.funaab.service.CourseRegService;
import com.funaab.service.LecturerService;
import com.funaab.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lecturer")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LecturerController {
    private final StudentService studentService;
    private final CourseRegService courseRegService;
    private final LecturerService lecturerService;
    @GetMapping("/registered-student-course")
    public ResponseEntity<List<Student>> getStudentsForCourse(@RequestParam("courseId") Long courseId){
        return lecturerService.getStudentsForCourse(courseId);
    }
    @GetMapping("/courses-lectured")
    public ResponseEntity<List<Course>> getCoursesForLecturer(@RequestParam("lecturerId") Long lecturerId){
        return lecturerService.getCoursesForLecturer(lecturerId);
    }

    @GetMapping("/total-student")
    public long getTotalStudentsForCourse(@RequestParam("courseId") Long courseId){
        return lecturerService.getTotalStudentsForCourse(courseId);
    }

    @GetMapping("/total-course")
    public int getTotalCoursesByLecturer(@RequestParam("staffId") String staffId) {
        return lecturerService.getTotalCoursesByLecturer(staffId);
    }


    @PostMapping("/update-score")
    public ResponseEntity<String> updateCourse(@RequestBody CourseUpdateRequest request){
        return lecturerService.updateCourse(request);
    }

    @GetMapping("/course-reg")
    public ResponseEntity<StudentResponse> getStudentWithCourses(@RequestParam("matricNo")String matricNo){
        return courseRegService.getStudentWithCourses(matricNo);
    }
}
