package com.funaab.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Builder
public class CourseRequest {
    private String matricNo;
    private Set<Long> courseIds;
}
