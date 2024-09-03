package com.funaab.service;

import com.funaab.dto.*;
import com.funaab.model.Course;
import com.funaab.model.CourseRegistration;
import com.funaab.model.Rectification;
import com.funaab.model.Student;
import com.funaab.model.enums.RegistrationStatus;
import com.funaab.repository.CourseRegRepository;
import com.funaab.repository.CourseRepository;
import com.funaab.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseRegService {
   private final CourseRegRepository courseRegRepository;
   private final CourseRepository courseRepository;
   private  final StudentRepository studentRepository;
    @Transactional
    public ResponseEntity<String> registerCourse(CourseRequest request) {
        Optional<Student> studentOptional = studentRepository.findByMatricNo(request.getMatricNo());
        if (studentOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Student not found");
        }
        Student student = studentOptional.get();
        for (Long courseId : request.getCourseIds()) {
           Optional <Course> courseOptional = courseRepository.findById(courseId);
            if (courseOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Course with ID " + courseId + " not found");
            }
            Course course = courseOptional.get();
            // Create a new course registration
            CourseRegistration registration = new CourseRegistration();
            registration.setStudent(student);
            registration.setStatus("PENDING");
            registration.setSemester(request.getSemester());
            registration.setYear(request.getYear());
            registration.setRegistrationStatus(String.valueOf(request.getRegistrationStatus()));
            registration.setCourse(course);
            courseRegRepository.save(registration);
        }
        return ResponseEntity.ok("Registration successful");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<StudentResponse> getStudentWithCourses(String matricNo) {
        Optional<Student> studentOptional = studentRepository.findByMatricNo(matricNo);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = studentOptional.get();
        StudentResponse studentDTO = new StudentResponse();
        studentDTO.setId(student.getId());
        studentDTO.setMatricNo(student.getMatricNo());
        studentDTO.setCollege(student.getCollege());
        studentDTO.setDepartment(student.getDepartment());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setFullName(student.getFullName());
        studentDTO.setLevel(student.getLevel());
        studentDTO.setLevel(student.getLevel());
        studentDTO.setRole(student.getRole());
        studentDTO.setGender(student.getGender());
        studentDTO.setRegistrations(student.getRegistrations().stream()
                .map(this::toCourseRegistrationDto)
                .collect(Collectors.toSet()));
        return ResponseEntity.ok(studentDTO);
    }

    private CourseRegResponse toCourseRegistrationDto(CourseRegistration registration) {
        CourseRegResponse dto = new CourseRegResponse();
        dto.setId(registration.getId());
        dto.setCourses(toCourseDto(registration.getCourse()));
        dto.setTestScore(registration.getTestScore());
        dto.setRegistrationStatus(registration.getRegistrationStatus());
        dto.setExamScore(registration.getExamScore());
        dto.setGrade(registration.getGrade());
        dto.setSemester(registration.getSemester());
        dto.setYear(registration.getYear());
        dto.setStatus(registration.getStatus());
        dto.setCreatedDate(registration.getCreatedDate());
        dto.setUpdatedDate(registration.getUpdatedDate());
        return dto;
    }

    private CourseRespose toCourseDto(Course course) {
        CourseRespose dto = new CourseRespose();
        dto.setCourseTitle(course.getCourseTitle());
        dto.setCourseCode(course.getCourseCode());
        dto.setCourseUnit(course.getCourseUnit());
        dto.setStatus(course.getCourseStatus());
        return dto;
    }

    public ResponseEntity<List<CourseRespose>> getCourses() {
        List<Course> courseList = courseRepository.findAll();
        List<CourseRespose> courseResponses = new ArrayList<>();
        for (Course course : courseList) {
            CourseRespose courseResponse = new CourseRespose();
            courseResponse.setId(course.getId());
            courseResponse.setCourseUnit(course.getCourseUnit());
            courseResponse.setCourseCode(course.getCourseCode());
            courseResponse.setStatus(courseResponse.getStatus());
            courseResponses.add(courseResponse);
        }
        return ResponseEntity.ok(courseResponses);
    }

    public ResponseEntity<List<Course>> getAllCourses(String level){
        return ResponseEntity.ok(courseRepository.findAllByCourseLevel(level));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<StudentResponse> viewResult(String matricNo) {
        Optional<Student> studentOptional = studentRepository.findByMatricNo(matricNo);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = studentOptional.get();
        StudentResponse studentDTO = new StudentResponse();
        studentDTO.setId(student.getId());
        studentDTO.setMatricNo(student.getMatricNo());
        studentDTO.setCollege(student.getCollege());
        studentDTO.setDepartment(student.getDepartment());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setFullName(student.getFullName());
        studentDTO.setLevel(student.getLevel());
        studentDTO.setLevel(student.getLevel());
        studentDTO.setRole(student.getRole());
        studentDTO.setGender(student.getGender());
        Set<CourseRegResponse> approvedRegistrations = student.getRegistrations().stream()
                .filter(registration -> registration.getStatus().equals("APPROVED"))
                .map(this::toCourseRegistrationDto)
                .collect(Collectors.toSet());
        studentDTO.setRegistrations(approvedRegistrations);
        return ResponseEntity.ok(studentDTO);
    }

}
