package com.commerce.stock.messaging.consumer;

import com.commerce.stock.messaging.message.OrderCreatedMessage;
import com.commerce.stock.messaging.producer.StockProducer;
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
public class OrderCreatedConsumer {
    private final StockService stockService;
    private final StockProducer stockProducer;
    @KafkaListener(topics = "${spring.kafka.consumer.order-created.created.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(
            @Payload OrderCreatedMessage orderCreatedMessage,
            Acknowledgment ack
    ) {
        log.info("orderId: {}", orderCreatedMessage.orderId());
        if (ObjectUtils.isEmpty(orderCreatedMessage)) {
            throw new IllegalArgumentException("OrderCreatedMessage can not be null");
        }
        try  {
            stockService.updateStockBecauseOfOrderCreation(orderCreatedMessage.orderDTO());
            ack.acknowledge();
        } catch (Exception e) {
            log.error("orderId: {}, message: {}, error: {} when consuming order created message", orderCreatedMessage.orderId(), e.getLocalizedMessage(), e.toString());
            stockProducer.publishStockUpdateFailedMessage(orderCreatedMessage.orderId());
        }
    }
}
