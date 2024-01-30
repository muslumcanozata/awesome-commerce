package com.commerce.order.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderItemDTO(Long id, Long productId, double quantity, BigDecimal subtotal, LocalDate updatedAt, LocalDate createdAt){}