package com.commerce.order.messaging.message;

import com.commerce.order.model.dto.OrderDTO;

public record OrderCancelledMessage(OrderDTO orderDTO) {}
