package com.funaab.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@Builder
public class CourseUpdateRequest {
    private String matricNo;
    private Long courseId;
    private BigDecimal examScore;
    private BigDecimal testScore;
}
