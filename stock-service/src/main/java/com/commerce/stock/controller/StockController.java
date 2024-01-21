package com.commerce.stock.controller;

import com.commerce.stock.model.dto.StockDTO;
import com.commerce.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;

    @GetMapping("/{productId}")
    public ResponseEntity<StockDTO> getStockByProductId(@PathVariable Long productId) {
        StockDTO stockDto = stockService.getStockByProductId(productId);
        return ResponseEntity.ok(stockDto);
    }

    @PutMapping("/")
    public ResponseEntity<StockDTO> updateStock(@RequestBody StockDTO stockDto) {
        StockDTO updatedStock = stockService.updateStock(stockDto);
        return ResponseEntity.ok(updatedStock);
    }

    @PostMapping("/")
    public ResponseEntity<StockDTO> initializeStock(@RequestBody StockDTO stockDto) {
        StockDTO initializedStock = stockService.initializeStock(stockDto);
        return ResponseEntity.ok(initializedStock);
    }

    @PostMapping("/{productId}/{quantity}")
    public ResponseEntity<StockDTO> addStockQuantity(
            @PathVariable Long productId,
            @PathVariable double quantity) {
        StockDTO updatedStock = stockService.addStockQuantity(productId, quantity);
        return ResponseEntity.ok(updatedStock);
    }

    @GetMapping("/{productId}/{quantity}")
    public ResponseEntity<StockDTO> checkStockQuantity(
            @PathVariable Long productId,
            @PathVariable double quantity) {
        StockDTO stockDto = stockService.checkStockQuantity(productId, quantity);
        return ResponseEntity.ok(stockDto);
    }

    @PostMapping("/batch-check")
    public ResponseEntity<List<Long>> checkStockQuantityBatch(@RequestBody Map<Long, Double> productQuantityMap) {
        List<Long> notInStockProducts= stockService.checkStocksInBatch(productQuantityMap);
        return ResponseEntity.ok(notInStockProducts);
    }
}
