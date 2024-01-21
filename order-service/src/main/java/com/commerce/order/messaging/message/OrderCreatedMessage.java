package com.commerce.order.messaging.message;

import com.commerce.order.model.dto.OrderDTO;

public record OrderCreatedMessage(Long orderId, OrderDTO orderDTO) {}
