package com.commerce.stock.service;

import com.commerce.stock.model.dto.OrderDTO;
import com.commerce.stock.model.dto.StockDTO;

import java.util.List;
import java.util.Map;

public interface StockService {
    StockDTO getStockByProductId(Long productId);
    StockDTO updateStock(StockDTO stockDto);
    StockDTO initializeStock(StockDTO stockDto);
    StockDTO addStockQuantity(Long productId, double quantity);
    StockDTO subtractStockQuantity(Long productId, double quantity);
    StockDTO checkStockQuantity(Long productId, double quantity);
    List<Long> checkStocksInBatch(Map<Long, Double> productQuantityMap);
    void updateStockBecauseOfOrderCancellation(OrderDTO orderDTO);

    void updateStockBecauseOfOrderCreation(OrderDTO orderDTO);

    void updateStockBecauseOfOrderUpdate(OrderDTO oldOrderDTO, OrderDTO updatedOrderDTO);
}
