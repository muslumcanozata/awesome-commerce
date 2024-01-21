package com.commerce.order.messaging.consumer;

import com.commerce.order.messaging.message.StockUpdateFailedMessage;
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
public class StockUpdateFailedConsumer {
    private final OrderService orderService;

    @KafkaListener(topics = "${spring.kafka.consumer.stock-update-failed.created.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(
            @Payload StockUpdateFailedMessage stockUpdateFailedMessage,
            Acknowledgment ack
    ) {
        log.info("orderId: {}", stockUpdateFailedMessage.orderId());
        if (ObjectUtils.isEmpty(stockUpdateFailedMessage)) {
            throw new IllegalArgumentException("StockUpdateFailedEvent can not be null");
        }
        try  {
            orderService.cancelOrder(stockUpdateFailedMessage.orderId());
            ack.acknowledge();
        } catch (Exception e) {
            log.error("orderId: {}, message: {}, error: {}", stockUpdateFailedMessage.orderId(), e.getLocalizedMessage(), e.toString());
        }
    }
}
