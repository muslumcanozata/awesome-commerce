package com.commerce.stock.messaging.consumer;

import com.commerce.stock.messaging.message.OrderCancelledMessage;
import com.commerce.stock.service.StockService;
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
public class OrderCancelledConsumer {
    private final StockService stockService;
    @KafkaListener(topics = "${spring.kafka.consumer.order-cancelled.created.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(
            @Payload OrderCancelledMessage orderCancelledMessage,
            Acknowledgment ack
    ) {
        log.info("orderId: {}", orderCancelledMessage.orderDTO());
        if (ObjectUtils.isEmpty(orderCancelledMessage)) {
            throw new IllegalArgumentException("OrderCancelledMessage can not be null");
        }
        try  {
            stockService.updateStockBecauseOfOrderCancellation(orderCancelledMessage.orderDTO());
            ack.acknowledge();
        } catch (Exception e) {
            log.error("orderId: {}, message: {}, error: {} when consuming order cancelled message", orderCancelledMessage.orderDTO().id(), e.getLocalizedMessage(), e.toString());
        }
    }
}
