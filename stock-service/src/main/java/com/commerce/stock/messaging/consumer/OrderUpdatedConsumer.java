package com.commerce.stock.messaging.consumer;

import com.commerce.stock.messaging.message.OrderUpdatedMessage;
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
public class OrderUpdatedConsumer {
    private final StockService stockService;
    @KafkaListener(topics = "${spring.kafka.consumer.order-updated.created.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(
            @Payload OrderUpdatedMessage orderUpdatedMessage,
            Acknowledgment ack
    ) {
        log.info("oldOrderDTO: {}, updatedOrderDTO: {}", orderUpdatedMessage.updatedOrderDTO(), orderUpdatedMessage.updatedOrderDTO());
        if (ObjectUtils.isEmpty(orderUpdatedMessage)) {
            throw new IllegalArgumentException("OrderUpdatedMessage can not be null");
        }
        try  {
            stockService.updateStockBecauseOfOrderUpdate(orderUpdatedMessage.oldOrderDTO(), orderUpdatedMessage.updatedOrderDTO());
            ack.acknowledge();
        } catch (Exception e) {
            log.error("oldOrderDTOId: {}, updatedOrderDTOId: {}, message: {}, error: {} when consuming order updated message", orderUpdatedMessage.oldOrderDTO().id(), orderUpdatedMessage.oldOrderDTO().id(), e.getLocalizedMessage(), e.toString());
        }
    }
}
