package com.commerce.order.controller;

import com.commerce.order.model.dto.OrderDTO;
import com.commerce.order.model.enums.OrderStatus;
import com.commerce.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void createOrder(@RequestBody OrderDTO orderDTO) throws ExecutionException, InterruptedException {
        orderService.createOrder(orderDTO);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateOrder(@RequestBody OrderDTO orderDTO) {
        orderService.updateOrder(orderDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{orderId}/cancellation")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        boolean result = orderService.cancelOrder(orderId);
        if (!result) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @PutMapping("/{orderId}/status/{orderStatus}}")
    public ResponseEntity<Void> updateOrder(@PathVariable Long orderId, @PathVariable String orderStatus) {
        orderService.updateOrderStatus(orderId, OrderStatus.valueOf(orderStatus));
        return ResponseEntity.noContent().build();
    }
}
