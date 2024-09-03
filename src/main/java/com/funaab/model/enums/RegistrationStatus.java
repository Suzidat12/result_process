package com.funaab.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegistrationStatus {
    COMPLETED("COMPLETED"),
    PENDING("PENDING");

    private final String registrationStatus;
}
