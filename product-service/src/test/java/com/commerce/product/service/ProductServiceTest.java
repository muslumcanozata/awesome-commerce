package com.commerce.product.service;

import com.commerce.product.exception.ProductNotFoundException;
import com.commerce.product.mapper.CoreMapper;
import com.commerce.product.model.dto.ProductDTO;
import com.commerce.product.model.entity.Product;
import com.commerce.product.dao.ProductRepository;
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
        ProductDTO productDTOToCreate = ProductServiceTestUtil.createProductDTO();
        Product savedProduct = CoreMapper.INSTANCE.productDtoToProduct(productDTOToCreate);

        //when
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductDTO result = productService.createProduct(productDTOToCreate);

        //then
        assertNotNull(result);
        assertEquals(productDTOToCreate.id(), result.id());
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
        ProductDTO updatedProductDTO = ProductServiceTestUtil.createProductDTO();
        Product updatedProduct = CoreMapper.INSTANCE.productDtoToProduct(updatedProductDTO);

        //when
        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        var result = productService.updateProduct(productId, updatedProductDTO);

        //then
        assertNotNull(result);
        assertEquals(updatedProductDTO.id(), result.id());
        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_NonExistingId_ShouldThrowProductNotFoundException() {
        //given
        ProductDTO updatedProductDTO = ProductServiceTestUtil.createProductDTO();

        //when
        when(productRepository.existsById(updatedProductDTO.id())).thenReturn(false);

        //then
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(updatedProductDTO.id(), updatedProductDTO));
        verify(productRepository, times(1)).existsById(updatedProductDTO.id());
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
