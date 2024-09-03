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
    private BigDecimal examScore;
    @Column(name = "grade")
    private String grade;
    @Column(name = "semester")
    private String semester;
    @Column(name = "comments")
    private String comments;
    @Column(name = "year")
    private String year;
    @Column(name = "status")
    private String status;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "updated_date")
    private Date updatedDate;
    public void setGradeBasedOnScores() {
        if (testScore != null && examScore != null) {
            BigDecimal totalScore = testScore.add(examScore);
            BigDecimal thresholdA = BigDecimal.valueOf(70); // Minimum score for grade A
            BigDecimal thresholdB = BigDecimal.valueOf(60); // Minimum score for grade B
            BigDecimal thresholdC = BigDecimal.valueOf(50); // Minimum score for grade C
            BigDecimal thresholdD = BigDecimal.valueOf(40); // Minimum score for grade D
            BigDecimal thresholdE = BigDecimal.valueOf(30); // Minimum score for grade D

            if (totalScore.compareTo(BigDecimal.valueOf(100)) >= 0) {
                this.grade = "A"; // Example threshold for 'A'
            } else if (totalScore.compareTo(thresholdA) >= 0) {
                this.grade = "B"; // Example threshold for 'B'
            } else if (totalScore.compareTo(thresholdB) >= 0) {
                this.grade = "C";
            }  else if (totalScore.compareTo(thresholdC) >= 0) {
            this.grade = "C";
        } else if (totalScore.compareTo(thresholdD) >= 0) {
            this.grade = "D";
        }else if (totalScore.compareTo(thresholdE) >= 0) {
            this.grade = "E";
        } else {
                this.grade = "F"; // Default or other grade
            }
        }
    }
}
