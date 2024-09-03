package com.funaab.service;

import com.funaab.dto.*;
import com.funaab.model.*;
import com.funaab.model.enums.CourseStatus;
import com.funaab.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HodService {
    private final AdminRepository adminRepository;
    private final LecturerRepository lecturerRepository;
    private final CourseRepository courseRepository;
    private final RectificationRepository rectificationRepository;
    private final CourseRegRepository courseRegRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public ResponseEntity<String> registerAdmin(LecturerRequest request) {
        Optional<Admin> adminOptional = adminRepository.findByStaffId(request.getStaffNo());
        if (adminOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Staff no already exist");
        }
        Admin admin = Admin.builder().gender(request.getGender()).email(request.getEmail())
                .college(request.getCollege()).department(request.getDepartment()).fullName(request.getFullName())
                .staffId(request.getStaffNo()).password(passwordEncoder.encode(request.getPassword()))
                .createdDate(new Date()).role("ADMIN").status("ACTIVE")
                .build();
        adminRepository.save(admin);
        return ResponseEntity.ok("Admin registered successfully");
    }

    public JwtAuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()));
        Admin user = adminRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password ..."));
        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setStudent(null);
        jwtAuthenticationResponse.setLecturer(null);
        jwtAuthenticationResponse.setAdmin(user);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    @Transactional
    public ResponseEntity<String> assignCourseToLecturer(AssignedRequest request) {
        Lecturer lecturer = lecturerRepository.findByStaffId(request.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("Lecturer not found"));
        for (Long courseId : request.getCourseIds()) {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new IllegalArgumentException("Course not found"));
            course.setLecturer(lecturer);
            courseRepository.save(course);
            lecturer.getCourses().add(course);
        }
        lecturerRepository.save(lecturer); // Save the updated lecturer
        return ResponseEntity.ok("Coursed assigned successfully");
    }

    public ResponseEntity<String> approveCourseResult(ApproveResultRequest request) {
        CourseRegistration courseRegistration = courseRegRepository.findByCourse_Id(request.getCourseId()).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        if (request.getCourseStatus() == CourseStatus.REJECTED && (request.getComment() == null || request.getComment().trim().isEmpty())) {
            return ResponseEntity.badRequest().body("Comment is required when the course status is REJECTED");
        }
        courseRegistration.setStatus(String.valueOf(request.getCourseStatus()));
        courseRegistration.setComments(request.getComment());
        courseRegRepository.save(courseRegistration);
        return ResponseEntity.ok("Course approved successfully");
    }

    public ResponseEntity<List<RectificationResponse>> getAllRectifications() {
        List<Rectification> rectificationList = rectificationRepository.findAll();
        List<RectificationResponse> rectificationResponses = new ArrayList<>();
        for (Rectification rectification : rectificationList) {
            RectificationResponse rectificationResponse = new RectificationResponse();
            rectificationResponse.setId(rectification.getId());
            rectificationResponse.setMatricNo(rectification.getStudent().getMatricNo());
            rectificationResponse.setReasons(rectification.getReason());
            rectificationResponse.setCourseCode(rectification.getCourse().getCourseCode());
            rectificationResponse.setFullName(rectification.getStudent().getFullName());
            rectificationResponse.setComments(rectification.getComments());
            rectificationResponse.setStatus(rectification.getStatus());
            rectificationResponses.add(rectificationResponse);
        }
        return ResponseEntity.ok(rectificationResponses);
    }

    public ResponseEntity<String> updateRectification(RectificationUpdate update) {
        Rectification rectification = rectificationRepository.findById(update.getId())
                .orElseThrow(() -> new RuntimeException("Rectification not found"));

        rectification.setComments(update.getComments());
        rectification.setStatus(String.valueOf(update.getStatus()));
        rectification.setUpdatedDate(new Date());
        rectificationRepository.save(rectification);
        return ResponseEntity.ok("Rectification successfully updated");
    }

    public ResponseEntity<List<RectificationResponse>> getAllApprovedRectifications() {
        List<Rectification> rectificationList = rectificationRepository.findAll();
        List<RectificationResponse> rectificationResponses = rectificationList.stream()
                .filter(rectification -> CourseStatus.APPROVED.name().equals(rectification.getStatus())) // Filter by status
                .map(rectification -> {
                    RectificationResponse rectificationResponse = new RectificationResponse();
                    rectificationResponse.setMatricNo(rectification.getStudent().getMatricNo());
                    rectificationResponse.setReasons(rectification.getReason());
                    rectificationResponse.setCourseCode(rectification.getCourse().getCourseCode());
                    rectificationResponse.setFullName(rectification.getStudent().getFullName());
                    rectificationResponse.setComments(rectification.getComments());
                    rectificationResponse.setStatus(rectification.getStatus());
                    return rectificationResponse;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(rectificationResponses);
    }

}
