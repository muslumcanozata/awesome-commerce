package com.commerce.order.service;


import com.commerce.order.model.dto.OrderDTO;
import com.commerce.order.model.enums.OrderStatus;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface OrderService {
    void createOrder(OrderDTO orderDTO) throws ExecutionException, InterruptedException;
    OrderDTO getOrder(Long orderId);
    void updateOrder(OrderDTO orderDTO);
    void deleteOrder(Long orderId);
    boolean cancelOrder(Long orderId);
    List<OrderDTO> getOrdersByUserId(Long userId);

    void updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
