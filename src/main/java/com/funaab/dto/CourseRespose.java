package com.funaab.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CourseRespose {
    private Long id;
    private String courseTitle;
    private String courseCode;
    private String courseUnit;
    private String status;
}
