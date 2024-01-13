package com.commerce.order.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String productName;
    private String description;
    private double price;
    private String quantityType;
}
