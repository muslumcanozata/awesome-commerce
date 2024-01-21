package com.commerce.order.client.model;

public record StockDTO(Long id, String productId, double availableQuantity, QuantityType quantityType) {}

