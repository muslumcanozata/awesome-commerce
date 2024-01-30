package com.commerce.order.model.dto;

import com.commerce.order.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderDTO(Long id, Long userId, List<OrderItemDTO> orderItemDTOs, BigDecimal totalAmount, OrderStatus orderStatus, LocalDate updatedAt, LocalDate createdAt) {}
