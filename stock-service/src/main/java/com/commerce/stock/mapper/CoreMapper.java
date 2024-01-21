package com.commerce.stock.mapper;

import com.commerce.stock.model.dto.StockDTO;
import com.commerce.stock.model.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoreMapper {
    CoreMapper INSTANCE = Mappers.getMapper( CoreMapper.class );

    StockDTO stockToStockDto(Stock stock);

    Stock stockDtoToStock(StockDTO stockDto);

}
