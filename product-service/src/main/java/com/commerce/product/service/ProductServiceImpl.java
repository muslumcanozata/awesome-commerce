package com.commerce.product.service;

import com.commerce.product.exception.ProductNotFoundException;
import com.commerce.product.mapper.CoreMapper;
import com.commerce.product.model.dto.ProductDTO;
import com.commerce.product.model.entity.Product;
import com.commerce.product.dao.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductDTO createProduct(ProductDTO productDto) {
        if (ObjectUtils.isEmpty(productDto)) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        Product product = CoreMapper.INSTANCE.productDtoToProduct(productDto);
        Product createdProduct = productRepository.save(product);
        return CoreMapper.INSTANCE.productToProductDto(createdProduct);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO updatedProductDTO) {
        if (productRepository.existsById(productId)) {
            Product updatedProduct = CoreMapper.INSTANCE.productDtoToProduct(updatedProductDTO);
            updatedProduct.setId(productId);
            Product savedProduct = productRepository.save(updatedProduct);
            return CoreMapper.INSTANCE.productToProductDto(savedProduct);
        } else {
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<ProductDTO> searchProductsByCategory(String category) {
        return CoreMapper.INSTANCE.productsToProductDtos(searchProductsByCategoryWithSearchSession(category));
    }

    @Override
    public List<ProductDTO> searchProductsByProductName(String productName) {
        return CoreMapper.INSTANCE.productsToProductDtos(searchProductsByProductNameWithSearchSession(productName));
    }

    public List<Product> searchProductsByCategoryWithSearchSession(String category) {
        SearchSession searchSession = Search.session(entityManager);
        return searchSession.search(Product.class)
            .where(f -> f.match()
                .field("category")
                .matching(category))
            .fetchHits(20);
    }

    private List<Product> searchProductsByProductNameWithSearchSession(String productName) {
        SearchSession searchSession = Search.session(entityManager);
        return searchSession.search(Product.class)
            .where(f -> f.match()
                    .field("productName")
                    .matching(productName))
            .fetchHits(20);
    }
}
