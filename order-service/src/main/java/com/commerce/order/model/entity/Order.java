package com.commerce.order.model.entity;

import com.commerce.order.model.enums.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    @Id
    private String orderId;
    private String userId;
    @OneToMany
    private List<OrderItem> orderItems;
    private double totalAmount;
    private OrderStatus status;
}