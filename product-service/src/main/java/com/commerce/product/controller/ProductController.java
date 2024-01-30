package com.commerce.product.controller;

import com.commerce.product.model.dto.ProductDTO;
import com.commerce.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDto) {
        ProductDTO createdProductDTO = productService.createProduct(productDto);
        return ResponseEntity.ok(createdProductDTO);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO updatedProduct) {
        ProductDTO productDto = productService.updateProduct(productId, updatedProduct);
        return ObjectUtils.isEmpty(productDto) ? ResponseEntity.ok(productDto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<ProductDTO>> searchProductsByCategory(@PathVariable String category) {
        List<ProductDTO> productDtos = productService.searchProductsByCategory(category);
        return !CollectionUtils.isEmpty(productDtos) ? ResponseEntity.ok(productDtos) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{productName}")
    public ResponseEntity<List<ProductDTO>> searchProductsByProductName(@PathVariable String productName) {
        List<ProductDTO> productDtos = productService.searchProductsByProductName(productName);
        return !CollectionUtils.isEmpty(productDtos) ? ResponseEntity.ok(productDtos) : ResponseEntity.notFound().build();
    }
}
