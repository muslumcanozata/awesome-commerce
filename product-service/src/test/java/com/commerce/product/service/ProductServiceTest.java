package com.commerce.product.service;

import com.commerce.product.exception.ProductNotFoundException;
import com.commerce.product.mapper.CoreMapper;
import com.commerce.product.model.dto.ProductDto;
import com.commerce.product.model.entity.Product;
import com.commerce.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProduct_ShouldReturnCreatedProduct() {
        //given
        ProductDto productDtoToCreate = new ProductDto();
        productDtoToCreate.setId(1L);
        Product savedProduct = CoreMapper.INSTANCE.productDtoToProduct(productDtoToCreate);

        //when
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductDto result = productService.createProduct(productDtoToCreate);

        //then
        assertNotNull(result);
        assertEquals(productDtoToCreate.getId(), result.getId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void createProduct_WhenProductDtoIsNull_ShouldThrowIllegalArgumentExpception() {
        //then
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(null));
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void updateProduct_ExistingId_ShouldReturnUpdatedProduct() {
        //given
        Long productId = 1L;
        ProductDto updatedProductDto = new ProductDto();
        updatedProductDto.setId(productId);
        Product updatedProduct = CoreMapper.INSTANCE.productDtoToProduct(updatedProductDto);

        //when
        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        var result = productService.updateProduct(productId, updatedProductDto);

        //then
        assertNotNull(result);
        assertEquals(updatedProductDto.getId(), result.getId());
        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_NonExistingId_ShouldThrowProductNotFoundException() {
        //given
        Long productId = 1L;
        ProductDto updatedProductDto = new ProductDto();

        //when
        when(productRepository.existsById(productId)).thenReturn(false);

        //then
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productId, updatedProductDto));
        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, never()).save(any());
    }

    @Test
    void deleteProduct_ShouldDeleteProduct() {
        //given
        Long productId = 1L;

        //when
        productService.deleteProduct(productId);

        //then
        verify(productRepository, times(1)).deleteById(productId);
    }
}
