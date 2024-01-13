package com.commerce.product.service;

import com.commerce.product.exception.ProductNotFoundException;
import com.commerce.product.mapper.CoreMapper;
import com.commerce.product.model.dto.ProductDto;
import com.commerce.product.model.entity.Product;
import com.commerce.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        if (ObjectUtils.isEmpty(productDto)) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        Product product = CoreMapper.INSTANCE.productDtoToProduct(productDto);
        Product createdProduct = productRepository.save(product);
        return CoreMapper.INSTANCE.productToProductDto(createdProduct);
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto updatedProductDto) {
        if (productRepository.existsById(productId)) {
            Product updatedProduct = CoreMapper.INSTANCE.productDtoToProduct(updatedProductDto);
            updatedProduct.setId(productId);
            Product savedProduct = productRepository.save(updatedProduct);
            return CoreMapper.INSTANCE.productToProductDto(savedProduct);
        } else {
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        }
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
