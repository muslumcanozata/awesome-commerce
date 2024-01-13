package com.commerce.stock.model;

import jakarta.persistence.Entity;
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
public class Stock implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    @Id
    private String id;
    private String productId;
    private double availableQuantity;
}
