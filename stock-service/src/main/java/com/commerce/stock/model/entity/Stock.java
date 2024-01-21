package com.commerce.stock.model.entity;

import com.commerce.stock.model.enums.QuantityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Stock extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private double availableQuantity;
    @Enumerated(EnumType.STRING)
    private QuantityType quantityType;
}
