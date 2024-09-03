package com.funaab.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RectificationResponse {
    private Long id;
    private String matricNo;
    private String fullName;
   private String courseCode;
   private String reasons;
   private String comments;
    private String status;
}
