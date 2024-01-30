package com.commerce.product.model.dto;

import com.commerce.product.model.enums.QuantityType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductDTO(Long id, String productName, String description, String category, BigDecimal price, QuantityType quantityType, LocalDate updatedAt, LocalDate createdAt) {}