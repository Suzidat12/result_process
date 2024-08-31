package com.funaab.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CourseStatus {
    COMPULSORY("COMPULSORY"),
    ELECTIVE("ELECTIVE");


    private final String courseStatus;
}
