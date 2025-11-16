package ru.sergeev.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Systems {
    ERP("Enterprise Resource Planning"),
    CRM("Customer Relationship Management"),
    WMS("Warehouse Management System");

    private final String description;

    Systems(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name();
    }

    @JsonCreator
    public static Systems fromString(String value) {
        for (Systems system : Systems.values()) {
            if (system.name().equalsIgnoreCase(value)) {
                return system;
            }
        }
        throw new IllegalArgumentException("Неизвестная система: " + value);
    }
}