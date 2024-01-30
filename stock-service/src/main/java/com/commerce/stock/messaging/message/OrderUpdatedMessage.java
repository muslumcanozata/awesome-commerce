package com.commerce.stock.messaging.message;

import com.commerce.stock.model.dto.OrderDTO;

public record OrderUpdatedMessage(OrderDTO oldOrderDTO, OrderDTO updatedOrderDTO) {}
