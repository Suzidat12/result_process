package com.funaab.controller;

import com.funaab.dto.*;
import com.funaab.model.Course;
import com.funaab.model.Student;
import com.funaab.service.CourseRegService;
import com.funaab.service.HodService;
import com.funaab.service.LecturerService;
import com.funaab.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {
    private final HodService hodService;

    @PostMapping("/assign-course")
    public ResponseEntity<String> assignCourseToLecturer(@RequestBody AssignedRequest request){
        return hodService.assignCourseToLecturer(request);
    }

    @PostMapping("/approve-result")
    public ResponseEntity<String> approveCourseResult(@RequestBody ApproveResultRequest request){
        return hodService.approveCourseResult(request);
    }

    @GetMapping("/rectifications")
    public ResponseEntity<List<RectificationResponse>> getAllRectifications(){
        return hodService.getAllRectifications();
    }

    @PutMapping("/update-rectification")
    public ResponseEntity<String> updateRectification(@RequestBody RectificationUpdate update){
        return hodService.updateRectification(update);
    }

    @GetMapping("/rectified-courses")
    public ResponseEntity<List<RectificationResponse>> getAllRectifiedCourses(){
        return hodService.getAllApprovedRectifications();
    }

}
