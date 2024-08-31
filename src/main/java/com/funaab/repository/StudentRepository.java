package com.funaab.repository;

import com.funaab.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByMatricNo(String matricNo);
    Optional<Student> findByEmail(String email);
    List<Student> findAllByMatricNo(String matricNo);
}
