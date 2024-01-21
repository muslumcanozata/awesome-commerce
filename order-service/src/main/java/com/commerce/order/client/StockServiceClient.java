package com.commerce.order.client;

import com.commerce.order.client.model.StockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "stock-service", url = "${feign.client.config.stock-service.url}")
public interface StockServiceClient {
    @GetMapping("/{productId}")
    ResponseEntity<StockDTO> getStockByProductId(@PathVariable Long productId);

    @PutMapping("/")
    ResponseEntity<StockDTO> updateStock(@RequestBody StockDTO StockDTO);

    @PostMapping("/")
    ResponseEntity<StockDTO> initializeStock(@RequestBody StockDTO StockDTO);

    @PostMapping("/{productId}/{quantity}")
    ResponseEntity<StockDTO> addStockQuantity(@PathVariable Long productId, @PathVariable double quantity);

    @GetMapping("/{productId}/{quantity}")
    ResponseEntity<StockDTO> checkStockQuantity(@PathVariable Long productId, @PathVariable double quantity);

    @PostMapping("/batch-check")
    ResponseEntity<List<Long>> checkStockQuantityBatch(@RequestBody Map<Long, Double> productQuantityMap);
}
