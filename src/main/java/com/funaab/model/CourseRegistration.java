package com.funaab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
//@Indexed
@Table(name = "course_reg")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@SequenceGenerator(
        name = "course_reg_sequence_gen",
        sequenceName = "course_reg_seq",
        allocationSize = 1)
public class CourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_reg_sequence_gen")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "test_score")
    private BigDecimal testScore;

    @Column(name = "registration_status")
    private String registrationStatus;

    @Column(name = "exam_score")
    private BigDecimal ExamScore;
    @Column(name = "grade")
    private String grade;
    @Column(name = "semester")
    private String semester;
    @Column(name = "year")
    private String year;
    @Column(name = "status")
    private String status;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "updated_date")
    private Date updatedDate;

}
