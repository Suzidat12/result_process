package com.funaab.repository;

import com.funaab.model.Admin;
import com.funaab.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findByStaffId (String staffId);
    Optional<Admin> findByEmail(String email);
    List<Admin> findAllByStaffId(String staffId);
}
