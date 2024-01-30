package com.commerce.stock.model.dto;

import com.commerce.stock.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderDTO(Long id, Long userId, List<OrderItemDTO> orderItemDTOs, BigDecimal totalAmount, OrderStatus orderStatus, LocalDate updatedAt, LocalDate createdAt) {}
