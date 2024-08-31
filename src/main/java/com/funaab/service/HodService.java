package com.funaab.service;

import com.funaab.dto.AssignedRequest;
import com.funaab.dto.JwtAuthenticationResponse;
import com.funaab.dto.LecturerRequest;
import com.funaab.dto.LoginRequest;
import com.funaab.model.Admin;
import com.funaab.model.Course;
import com.funaab.model.Lecturer;
import com.funaab.repository.AdminRepository;
import com.funaab.repository.CourseRepository;
import com.funaab.repository.LecturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HodService {
   private final AdminRepository adminRepository;
   private final LecturerRepository lecturerRepository;
   private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

  public ResponseEntity<String> registerAdmin(LecturerRequest request){
        Optional<Admin> adminOptional = adminRepository.findByStaffId(request.getStaffNo());
      if(adminOptional.isPresent()){
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
    public JwtAuthenticationResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),request.getPassword()));
        Admin user = adminRepository.findByEmail(request.getEmail()).orElseThrow(()->new IllegalArgumentException("Invalid email or password ..."));
        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setStudent(null);
        jwtAuthenticationResponse.setLecturer(null);
        jwtAuthenticationResponse.setAdmin(user);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    @Transactional
    public void assignCourseToLecturer(AssignedRequest request) {
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
    }
}
