package ru.sergeev.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorMessages {
    EMPTY(""),
    VALIDATION_EXCEPTION("Ошибка валидации"),
    UNSUPPORTED_EXCEPTION("UID '123' не поддерживается"),
    UNKNOWN_EXCEPTION("Неизвестная ошибка");

    private final String desc;
    ErrorMessages(String desc) { this.desc = desc; }

    @JsonValue
    public String getDescription() { return desc; }
}