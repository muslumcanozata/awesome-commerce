package com.commerce.user.model.enums;

public enum UserRole {
    ADMIN("Admin"),
    CUSTOMER("Customer"),
    SALESPERSON("Salesperson");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}