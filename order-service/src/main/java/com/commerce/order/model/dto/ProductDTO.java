package com.commerce.order.model.dto;

import java.time.LocalDate;

public record ProductDTO (Long id, String productName, String description, double price, String quantityType, LocalDate updatedAt, LocalDate createdAt) {}
