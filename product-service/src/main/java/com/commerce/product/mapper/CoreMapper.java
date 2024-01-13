package com.commerce.product.mapper;

import com.commerce.product.model.dto.ProductDto;
import com.commerce.product.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoreMapper {
    CoreMapper INSTANCE = Mappers.getMapper( CoreMapper.class );

    ProductDto productToProductDto(Product product);

    Product productDtoToProduct(ProductDto product);

}
