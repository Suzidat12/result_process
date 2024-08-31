package com.funaab.service;

import com.funaab.dto.CourseRegResponse;
import com.funaab.dto.CourseRequest;
import com.funaab.dto.CourseRespose;
import com.funaab.dto.StudentResponse;
import com.funaab.model.Course;
import com.funaab.model.CourseRegistration;
import com.funaab.model.Student;
import com.funaab.repository.CourseRegRepository;
import com.funaab.repository.CourseRepository;
import com.funaab.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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

}
