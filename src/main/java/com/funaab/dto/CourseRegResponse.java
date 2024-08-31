package com.funaab.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CourseRegResponse {
    private Long id;
    private CourseRespose courses;
    private BigDecimal testScore;
    private String registrationStatus;
    private BigDecimal examScore;
    private String grade;
    private String semester;
    private String year;
    private String status;
    private Date createdDate;
    private Date updatedDate;
}
