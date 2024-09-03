package com.funaab.dto;

import lombok.Data;

@Data
public class RectificationRequest {
    private String matricNo;
    private Long courseId;
    private String reasons;
}
