package com.commerce.stock.messaging.producer;

import com.commerce.stock.messaging.message.OrderCreatedMessage;
import com.commerce.stock.messaging.message.StockUpdateFailedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class StockProducer {
    private final KafkaTemplate<String, OrderCreatedMessage> kafkaTemplate;

    @Value("${spring.kafka.producer.stock-update-failed.created.topic}")
    private String stockUpdateFailedTopic;
    public void publishStockUpdateFailedMessage(Long orderId) {
        try {
            StockUpdateFailedMessage stockUpdateFailedMessage = new StockUpdateFailedMessage(orderId);
            Message<StockUpdateFailedMessage> message =
                    MessageBuilder.withPayload(stockUpdateFailedMessage)
                            .setHeader(KafkaHeaders.TOPIC, stockUpdateFailedTopic)
                            .build();

            kafkaTemplate.send(message).get();
        } catch (Exception e) {
            log.error("orderId: {}, message: {}, error: {}, occurred when publishing stock update failed message", orderId, e.getLocalizedMessage(), e.toString());
            throw new RuntimeException(e);
        }
    }
}
