package com.commerce.order.service;

import com.commerce.order.client.StockServiceClient;
import com.commerce.order.messaging.producer.OrderProducer;
import com.commerce.order.mapper.CoreMapper;
import com.commerce.order.model.dto.OrderDTO;
import com.commerce.order.model.entity.Order;
import com.commerce.order.model.entity.OrderItem;
import com.commerce.order.model.enums.OrderStatus;
import com.commerce.order.dao.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final LockService lockService;
    private final StockServiceClient stockService;
    @Override
    @Transactional
    public void createOrder(OrderDTO orderDTO) {
        if (ObjectUtils.isEmpty(orderDTO)) {
            throw new IllegalArgumentException("OrderDTO can not be null");
        }
        Order order = CoreMapper.INSTANCE.orderDTOToOrder(orderDTO);
        checkStockForOrderItems(order);
        calculateTotalAmount(order);
        orderRepository.save(order);
        orderProducer.publishOrderCreatedMessage(order.getId(), CoreMapper.INSTANCE.orderToOrderDTO(order));
    }

    @Override
    public OrderDTO getOrder(Long orderId) {
        if(ObjectUtils.isEmpty(orderId)) {
            throw new IllegalArgumentException("OrderId can not be null");
        }
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            return CoreMapper.INSTANCE.orderToOrderDTO(order);
        }
        //TODO order not found exception
        return null;
    }

    @Override
    @Transactional
    public void updateOrder(OrderDTO orderDTO) {
        if (ObjectUtils.isEmpty(orderDTO)) {
            throw new IllegalArgumentException("OrderDTO can not be null");
        }
        Order existedOrder = orderRepository.findById(orderDTO.id()).orElseThrow(() ->
                new IllegalArgumentException("Order does not exist")
        );
        try {
            lockService.lockWithKey(orderDTO.id().toString());
            Order order = CoreMapper.INSTANCE.orderDTOToOrder(orderDTO);
            Order updatedOrder = orderRepository.save(order);
            orderProducer.publishOrderUpdatedMessage(CoreMapper.INSTANCE.orderToOrderDTO(existedOrder), CoreMapper.INSTANCE.orderToOrderDTO(updatedOrder));
        } catch (InterruptedException e) {
            log.error("Error occurred when locking the order: {}", e.getLocalizedMessage());
        } finally {
            lockService.releaseLock(orderDTO.id().toString());
        }
    }

    @Override
    public void deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException("Order does not exist");
        }
        try {
            lockService.lockWithKey(orderId.toString());
            orderRepository.deleteById(orderId);
        } catch (InterruptedException e) {
            log.error("Error occurred when locking the order: {}", e.getLocalizedMessage());
        } finally {
            lockService.releaseLock(orderId.toString());
        }
    }

    @Override
    @Transactional
    public boolean cancelOrder(Long orderId) {
        try {
            lockService.lockWithKey(orderId.toString());
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                if (!order.isCancelled()) {
                    order.setStatus(OrderStatus.CANCELLED);
                    orderRepository.save(order);
                    orderProducer.publishOrderCancelledMessage(CoreMapper.INSTANCE.orderToOrderDTO(order));
                }
                throw new IllegalArgumentException("Order is already cancelled");
            }
            throw new IllegalArgumentException("Order does not exist");
        } catch (InterruptedException e) {
            log.error("Error occurred when locking the order: {} in cancelOrder", e.getLocalizedMessage());
        } finally {
            lockService.releaseLock(orderId.toString());
        }
        return true;
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        //TODO order not found exception
        List<Order> orders = orderRepository.findByUserId(userId);
        return CoreMapper.INSTANCE.ordersToOrderDTOs(orders);
    }

    private void calculateTotalAmount(Order order) {
        if (ObjectUtils.isEmpty(order) && CollectionUtils.isEmpty(order.getOrderItems())) {
            throw new IllegalArgumentException("Order and order items can not be null");
        }
        BigDecimal totalAmount = order.getOrderItems().stream().map(obj -> obj.getUnitPrice()
                .multiply(BigDecimal.valueOf(obj.getQuantity())
                        .multiply(BigDecimal.valueOf(obj.getDiscountPercentage(), 2)))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);
    }

    private void checkStockForOrderItems(Order order) {
        Map<Long, Double> productQuantityMap = order.getOrderItems().stream()
                .collect(Collectors.toMap(OrderItem::getProductId, OrderItem::getQuantity));
        List<Long> notInStockProducts = stockService.checkStockQuantityBatch(productQuantityMap).getBody();
        if (!CollectionUtils.isEmpty(notInStockProducts)) {
            throw new IllegalArgumentException("Stock is not enough for products: " + notInStockProducts);
        }
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            if (OrderStatus.SHIPPED.equals(orderStatus) || OrderStatus.DELIVERED.equals(orderStatus)) {
                Order order = orderOptional.get();
                order.setStatus(orderStatus);
                orderRepository.save(order);
            } else if (OrderStatus.CANCELLED.equals(orderStatus)) {
                cancelOrder(orderId);
            }
        }
    }
}
