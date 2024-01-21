package com.commerce.order.messaging.consumer;

import com.commerce.order.messaging.message.OrderStatusUpdateMessage;
import com.commerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderStatusUpdateConsumer {
    private final OrderService orderService;

    @KafkaListener(topics = "${spring.kafka.consumer.order-status-update.created.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(
            @Payload OrderStatusUpdateMessage orderStatusUpdateMessage,
            Acknowledgment ack
    ) {
        log.info("OrderStatusUpdateMessage.consume: {}", orderStatusUpdateMessage);
        if (ObjectUtils.isEmpty(orderStatusUpdateMessage)) {
            log.error("OrderStatusUpdateConsumer.consume: orderStatusUpdateMessage is null");
            throw new IllegalArgumentException("OrderStatusUpdateConsumer.consume: orderStatusUpdateMessage is null");
        }
        orderService.updateOrderStatus(orderStatusUpdateMessage.orderId(), orderStatusUpdateMessage.orderStatus());
        ack.acknowledge();
    }
}
