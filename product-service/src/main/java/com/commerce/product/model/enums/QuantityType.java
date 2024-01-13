package com.commerce.product.model.enums;

public enum QuantityType {
    UNIT("Unit"),
    KILOGRAM("Kilogram"),
    LITER("Liter");

    private final String displayName;
    QuantityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
