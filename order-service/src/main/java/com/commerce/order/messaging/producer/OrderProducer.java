package com.commerce.order.messaging.producer;

import com.commerce.order.messaging.message.OrderCancelledMessage;
import com.commerce.order.messaging.message.OrderCreatedMessage;
import com.commerce.order.messaging.message.OrderUpdatedMessage;
import com.commerce.order.model.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.producer.order-created.created.topic}")
    private String ORDER_CREATED_MESSAGE_TOPIC;
    @Value("${spring.kafka.producer.order-updated.created.topic}")
    private String ORDER_UPDATED_MESSAGE_TOPIC;
    @Value("${spring.kafka.producer.order-cancelled.created.topic}")
    private String ORDER_CANCELLED_MESSAGE_TOPIC;

    public void publishOrderCreatedMessage(Long orderId, OrderDTO orderDTO) {
        try {
            OrderCreatedMessage orderCreatedMessage = new OrderCreatedMessage(orderId, orderDTO);
            Message<OrderCreatedMessage> message =
                    MessageBuilder.withPayload(orderCreatedMessage)
                            .setHeader(KafkaHeaders.TOPIC, ORDER_CREATED_MESSAGE_TOPIC)
                            .build();

            kafkaTemplate.send(message).get();
        } catch (Exception e) {
            log.error("orderId: {}, message: {}, error: {}, occurred when publishing order created message", orderId, e.getLocalizedMessage(), e.toString());
            throw new RuntimeException(e);
        }
    }

    public void publishOrderUpdatedMessage(OrderDTO oldOrderDTO, OrderDTO updatedOrderDTO) {
        try {
            OrderUpdatedMessage orderUpdatedMessage = new OrderUpdatedMessage(oldOrderDTO, updatedOrderDTO);
            Message<OrderUpdatedMessage> msg =
                    MessageBuilder.withPayload(orderUpdatedMessage)
                            .setHeader(KafkaHeaders.TOPIC, ORDER_UPDATED_MESSAGE_TOPIC)
                            .build();

            kafkaTemplate.send(msg).get();
        } catch (Exception e) {
            log.error("oldOrderId: {}, updatedOrderId: {}, message: {}, error: {}, occurred when publishing order updated message", oldOrderDTO.id(), updatedOrderDTO.id(), e.getLocalizedMessage(), e.toString());
            throw new RuntimeException(e);
        }
    }

    public void publishOrderCancelledMessage(OrderDTO cancelledOrderDTO) {
        try {
            OrderCancelledMessage orderCancelledMessage = new OrderCancelledMessage(cancelledOrderDTO);
            Message<OrderCancelledMessage> msg =
                    MessageBuilder.withPayload(orderCancelledMessage)
                            .setHeader(KafkaHeaders.TOPIC, ORDER_CANCELLED_MESSAGE_TOPIC)
                            .build();

            kafkaTemplate.send(msg).get();
        } catch (Exception e) {
            log.error("cancelledOrderId: {}, message: {}, error: {}, occurred when publishing order updated message", cancelledOrderDTO.id(), e.getLocalizedMessage(), e.toString());
            throw new RuntimeException(e);
        }
    }
}
