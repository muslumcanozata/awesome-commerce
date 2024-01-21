package com.commerce.stock.messaging.message;

import com.commerce.stock.model.dto.OrderDTO;

public record OrderCancelledMessage(OrderDTO orderDTO) {}
