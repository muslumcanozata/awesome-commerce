package com.commerce.product.service;

import com.commerce.product.model.dto.ProductDTO;
import com.commerce.product.model.enums.QuantityType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductServiceTestUtil {
    public static ProductDTO createProductDTO() {
        return new ProductDTO(1L, "productName", "description", "category", BigDecimal.valueOf(100), QuantityType.UNIT, LocalDate.now(), LocalDate.now());
    }
}
