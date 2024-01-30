package com.commerce.order.model.entity;

import com.commerce.order.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Audited
@AuditTable(value = "AU_ORDERS")
@Entity
@Table(name = "orders")
public class Order extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @OneToMany
    private List<OrderItem> orderItems;
    private BigDecimal totalAmount;
    private OrderStatus status;

    public boolean isCancelled() {
        return OrderStatus.CANCELLED.equals(status);
    }
}