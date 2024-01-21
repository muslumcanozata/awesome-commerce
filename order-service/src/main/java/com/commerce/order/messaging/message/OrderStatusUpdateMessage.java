package com.commerce.order.messaging.message;

import com.commerce.order.model.enums.OrderStatus;

public record OrderStatusUpdateMessage(Long orderId, OrderStatus orderStatus) {}
