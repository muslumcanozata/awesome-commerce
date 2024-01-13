package com.commerce.product.service;

import com.commerce.product.model.dto.ProductDto;

public interface ProductService {
    ProductDto createProduct(ProductDto product);
    ProductDto updateProduct(Long productId, ProductDto updatedProductDto);
    void deleteProduct(Long productId);
}
