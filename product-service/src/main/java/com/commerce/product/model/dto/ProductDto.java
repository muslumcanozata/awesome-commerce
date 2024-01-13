package com.commerce.product.model.dto;

import com.commerce.product.model.enums.QuantityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Setter
@Getter
public class ProductDto {
    private Long id;
    private String productName;
    private String description;
    private BigDecimal price;
    private QuantityType quantityType;
}