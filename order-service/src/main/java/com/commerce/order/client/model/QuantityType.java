package com.commerce.order.client.model;

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
