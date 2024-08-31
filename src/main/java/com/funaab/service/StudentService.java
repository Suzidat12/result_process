package com.funaab.service;

import com.funaab.dto.JwtAuthenticationResponse;
import com.funaab.dto.LoginRequest;
import com.funaab.dto.StudentRequest;
import com.funaab.model.Student;
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
public class StudentService {
   private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

  public ResponseEntity<String> registerStudent(StudentRequest request){
        Optional<Student> studentOptional = studentRepository.findByMatricNo(request.getMatricNo());
      if(studentOptional.isPresent()){
          return ResponseEntity.badRequest().body("Student Matric no already exist");
      }
        Student student = Student.builder().gender(request.getGender()).email(request.getEmail())
                .college(request.getCollege()).department(request.getDepartment()).fullName(request.getFullName())
                .matricNo(request.getMatricNo()).password(passwordEncoder.encode(request.getPassword())).level(request.getLevel())
                .createdDate(new Date()).role("STUDENT").status("ACTIVE")
                .build();
        studentRepository.save(student);
        return ResponseEntity.ok("Student registered successfully");
    }
    public JwtAuthenticationResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),request.getPassword()));
        Student user = studentRepository.findByEmail(request.getEmail()).orElseThrow(()->new IllegalArgumentException("Invalid email or password ..."));
        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setStudent(user);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }
   public ResponseEntity<List<Student>> getAllStudent(){
        return ResponseEntity.ok(studentRepository.findAll());
    }

  public   ResponseEntity<List<Student>> getAllStudentByMaticNo(String matricNo){
        return ResponseEntity.ok(studentRepository.findAllByMatricNo(matricNo));
    }
}
