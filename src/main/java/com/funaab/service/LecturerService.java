package com.funaab.service;

import com.funaab.dto.JwtAuthenticationResponse;
import com.funaab.dto.LecturerRequest;
import com.funaab.dto.LoginRequest;
import com.funaab.dto.StudentRequest;
import com.funaab.model.CourseRegistration;
import com.funaab.model.Lecturer;
import com.funaab.model.Student;
import com.funaab.repository.CourseRegRepository;
import com.funaab.repository.CourseRepository;
import com.funaab.repository.LecturerRepository;
import com.funaab.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LecturerService {
   private final LecturerRepository lecturerRepository;
   private final CourseRegRepository courseRegRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

  public ResponseEntity<String> registerLecturer(LecturerRequest request){
        Optional<Lecturer> lecturerOptional = lecturerRepository.findByStaffId(request.getStaffNo());
      if(lecturerOptional.isPresent()){
          return ResponseEntity.badRequest().body("Staff no already exist");
      }
        Lecturer lecturer = Lecturer.builder().gender(request.getGender()).email(request.getEmail())
                .college(request.getCollege()).department(request.getDepartment()).fullName(request.getFullName())
                .staffId(request.getStaffNo()).password(passwordEncoder.encode(request.getPassword()))
                .createdDate(new Date()).role("LECTURER").status("ACTIVE")
                .build();
        lecturerRepository.save(lecturer);
        return ResponseEntity.ok("Lecturer registered successfully");
    }
    public JwtAuthenticationResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),request.getPassword()));
        Lecturer user = lecturerRepository.findByEmail(request.getEmail()).orElseThrow(()->new IllegalArgumentException("Invalid email or password ..."));
        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setStudent(null);
        jwtAuthenticationResponse.setLecturer(user);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }
   public ResponseEntity<List<Lecturer>> getAllStudent(){
        return ResponseEntity.ok(lecturerRepository.findAll());
    }

    public int getTotalCoursesByLecturer(String staffId) {
        Lecturer lecturer = lecturerRepository.findByStaffId(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Lecturer not found"));
        return lecturer.totalCourse();
    }

  public   ResponseEntity<List<Lecturer>> getAllLecturerByStaffId(String staffId){
        return ResponseEntity.ok(lecturerRepository.findAllByStaffId(staffId));
    }
    public long getTotalStudentsForCourse(Long courseId) {
        return courseRegRepository.countByCourseId(courseId);
    }

    public List<Student> getStudentsForCourse(Long courseId) {
        return courseRegRepository.findStudentsByCourseId(courseId);
    }
}
