package com.commerce.stock.model.dto;

import com.commerce.stock.model.enums.QuantityType;

import java.time.LocalDate;

public record StockDTO(Long id, Long productId, double availableQuantity, QuantityType quantityType, LocalDate updatedAt, LocalDate createdAt) {}
