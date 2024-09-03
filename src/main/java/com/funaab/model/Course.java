package com.funaab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Indexed
@Table(name = "course")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@SequenceGenerator(
        name = "course_sequence_gen",
        sequenceName = "course_seq",
        allocationSize = 1)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_sequence_gen")
    private Long id;
    @Column(name = "course_code", unique = true)
    private String courseCode;

    @Column(name = "course_title")
    private String courseTitle;

    @Column(name = "course_unit")
    private String courseUnit;

    @Column(name = "course_level")
    private String courseLevel;

    @JsonIgnore
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<CourseRegistration> registrations = new HashSet<>();

    @Column(name = "course_status")
    private String courseStatus;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer; // Lecturer teaching the course


}
