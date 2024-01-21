package com.commerce.stock.model.entity;

import com.commerce.stock.model.enums.QuantityType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
    private Long id;
    private Long productId;
    private double availableQuantity;
    @Enumerated(EnumType.STRING)
    private QuantityType quantityType;
}
