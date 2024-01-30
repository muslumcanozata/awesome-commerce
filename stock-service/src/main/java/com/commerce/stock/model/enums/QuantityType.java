package com.commerce.stock.model.enums;

import lombok.Getter;

@Getter
public enum QuantityType {
    UNIT("Unit"),
    KILOGRAM("Kilogram"),
    LITER("Liter");

    private final String displayName;
    QuantityType(String displayName) {
        this.displayName = displayName;
    }
}
