package com.funaab.repository;

import com.funaab.model.Lecturer;
import com.funaab.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer,Long> {
    Optional<Lecturer> findByStaffId (String staffId);
    Optional<Lecturer> findByEmail(String email);
    List<Lecturer> findAllByStaffId(String staffId);
}
