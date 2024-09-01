package com.funaab.repository;

import com.funaab.model.Course;
import com.funaab.model.CourseRegistration;
import com.funaab.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRegRepository extends JpaRepository<CourseRegistration, Long> {
    long countByCourseId(Long courseId);
    Optional<CourseRegistration> findByStudent(Student student);
    @Query("SELECT c.student FROM CourseRegistration c WHERE c.course.id = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId") Long courseId);
}
