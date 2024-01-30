package com.commerce.stock.dao;

import com.commerce.stock.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findByProductId(Long productId);
    List<Stock> findStocksByProductIdIn(List<Long> productIds);
}
