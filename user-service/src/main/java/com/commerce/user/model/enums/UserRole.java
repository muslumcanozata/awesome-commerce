package com.commerce.user.model.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("Admin"),
    CUSTOMER("Customer"),
    SALESPERSON("Salesperson");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }
}