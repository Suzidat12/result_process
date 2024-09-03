package com.funaab.dto;

import com.funaab.model.enums.CourseStatus;
import lombok.Data;

@Data
public class ApproveResultRequest {
   private Long courseId;
   private CourseStatus courseStatus;
   private String comment;
}
