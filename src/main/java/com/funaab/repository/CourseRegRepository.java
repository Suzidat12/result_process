package com.funaab.repository;

import com.funaab.model.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRegRepository extends JpaRepository<CourseRegistration, Long> {
}
