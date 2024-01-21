package com.commerce.product.mapper;

import com.commerce.product.model.dto.ProductDTO;
import com.commerce.product.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CoreMapper {
    CoreMapper INSTANCE = Mappers.getMapper( CoreMapper.class );

    ProductDTO productToProductDto(Product product);
    Product productDtoToProduct(ProductDTO product);
    List<ProductDTO> productsToProductDtos(List<Product> products);

}
