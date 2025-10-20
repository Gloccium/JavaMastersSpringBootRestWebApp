package ru.sergeev.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCodes {
    EMPTY(""), SUCCESS("success"), FAILED("failed");

    private final String name;
    ErrorCodes(String name) { this.name = name; }

    @JsonValue
    public String getName() { return name; }
}