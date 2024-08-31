package com.funaab.repository;

import com.funaab.model.Course;
import com.funaab.model.CourseRegistration;
import com.funaab.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRegRepository extends JpaRepository<CourseRegistration, Long> {
    long countByCourseId(Long courseId);
    @Query("SELECT c.student FROM CourseRegistration c WHERE c.course.id = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId") Long courseId);
}
