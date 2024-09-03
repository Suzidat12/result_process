package com.funaab.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CourseStatus {
    APPROVED("APPROVED"),
    REJECTED("REJECTED");


    private final String courseStatus;
}
