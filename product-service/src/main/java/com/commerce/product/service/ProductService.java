package com.commerce.product.service;

import com.commerce.product.model.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO product);
    ProductDTO updateProduct(Long productId, ProductDTO updatedProductDTO);
    void deleteProduct(Long productId);
    List<ProductDTO> searchProductsByCategory(String category);
    List<ProductDTO> searchProductsByProductName(String productName);

}
