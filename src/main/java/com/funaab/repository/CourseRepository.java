package com.funaab.repository;

import com.funaab.model.Course;
import com.funaab.model.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByLecturerId(Long lecturerId);

}
