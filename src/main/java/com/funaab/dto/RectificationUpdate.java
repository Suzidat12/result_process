package com.funaab.dto;

import com.funaab.model.enums.CourseStatus;
import lombok.Data;

@Data
public class RectificationUpdate {
    private Long id;
    private String comments;
    private CourseStatus status;
}
