package com.commerce.stock.service;

import com.commerce.stock.dao.StockRepository;
import com.commerce.stock.exception.StockAlreadyExistsException;
import com.commerce.stock.exception.StockNotFoundException;
import com.commerce.stock.mapper.CoreMapper;
import com.commerce.stock.model.dto.OrderDTO;
import com.commerce.stock.model.dto.OrderItemDTO;
import com.commerce.stock.model.dto.StockDTO;
import com.commerce.stock.model.entity.Stock;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final LockService lockService;
    
    @Override
    public StockDTO getStockByProductId(Long productId) {
        if(productId == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
        return CoreMapper.INSTANCE.stockToStockDto(stockRepository.findByProductId(productId));
    }

    @Override
    public StockDTO updateStock(StockDTO stockDto) {
        if (!ObjectUtils.isEmpty(stockDto) && !stockRepository.existsById(stockDto.id())) {
            throw new IllegalArgumentException("Stock cannot be null");
        }
        StockDTO savedStockDTO = null;
        try {
            lockService.lockWithKey(stockDto.productId());
            Stock stock = stockRepository.save(CoreMapper.INSTANCE.stockDtoToStock(stockDto));
            savedStockDTO = CoreMapper.INSTANCE.stockToStockDto(stock);
        } catch (InterruptedException e) {
            log.error("Error: {} when updating stock", e.getLocalizedMessage());
        } finally {
            lockService.releaseLock(stockDto.productId());
        }
        if (savedStockDTO != null) {
            return savedStockDTO;
        }
        throw new StockNotFoundException("Stock not found");
    }

    @Override
    public StockDTO initializeStock(StockDTO stockDto) {
        if (!ObjectUtils.isEmpty(stockDto)) {
            throw new IllegalArgumentException("Stock cannot be null");
        }
        if (stockRepository.existsById(stockDto.id())) {
            throw new StockAlreadyExistsException("Stock already exists");
        }
        return CoreMapper.INSTANCE.stockToStockDto(stockRepository.save(CoreMapper.INSTANCE.stockDtoToStock(stockDto)));
    }

    @Override
    public StockDTO addStockQuantity(Long productId, double quantity) {
        if(productId == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
        Stock stock = null;
        try {
            lockService.lockWithKey(productId);
            stock = stockRepository.findByProductId(productId);
            if(!ObjectUtils.isEmpty(stock)) {
                stock.setAvailableQuantity(quantity + stock.getAvailableQuantity());
                stockRepository.save(stock);
            } else {
                throw new StockNotFoundException("Stock not found");
            }
        } catch (InterruptedException e) {
            log.error("Error: {} when adding stock", e.getLocalizedMessage());
        } finally {
            lockService.releaseLock(productId);
        }
        return CoreMapper.INSTANCE.stockToStockDto(stock);
    }

    @Override
    public StockDTO subtractStockQuantity(Long productId, double quantity) {
        if(productId == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
        Stock stock = null;
        try {
            lockService.lockWithKey(productId);
            stock = stockRepository.findByProductId(productId);
            if (quantity > stock.getAvailableQuantity()) {
                throw new IllegalArgumentException("Quantity cannot be greater than available quantity");
            }
            if(!ObjectUtils.isEmpty(stock)) {
                stock.setAvailableQuantity(quantity - stock.getAvailableQuantity());
                stockRepository.save(stock);
            } else {
                throw new StockNotFoundException("Stock not found");
            }
        } catch (InterruptedException e) {
            log.error("Error: {} when subtracting stock", e.getLocalizedMessage());
        } finally {
            lockService.releaseLock(productId);
        }
        return CoreMapper.INSTANCE.stockToStockDto(stock);
    }

    @Override
    public StockDTO checkStockQuantity(Long productId, double quantity) {
        if (productId == null) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
        Stock stock = stockRepository.findByProductId(productId);
        if (stock != null) {
            if (stock.getAvailableQuantity() >= quantity) {
                return CoreMapper.INSTANCE.stockToStockDto(stock);
            }
        }
        throw new StockNotFoundException("Stock not found");
    }
    @Override
    public List<Long> checkStocksInBatch(Map<Long, Double> productQuantityMap) {
        if (productQuantityMap.isEmpty()) {
            throw new IllegalArgumentException("Product ids its quantities map cannot be null or empty");
        }
        List<Long> notInStockProducts = new ArrayList<>();
        List<Stock> stocks = stockRepository.findStocksByProductIdIn(new ArrayList<>(productQuantityMap.keySet()));
        for (Stock stock : stocks) {
            if (!ObjectUtils.isEmpty(stock)) {
                if (stock.getAvailableQuantity() < productQuantityMap.get(stock.getProductId())) {
                    notInStockProducts.add(stock.getProductId());
                }
                continue;
            }
            notInStockProducts.add(stock.getProductId());
        }
        return notInStockProducts;
    }

    @Override
    public void updateStockBecauseOfOrderCancellation(OrderDTO orderDTO) {
        if (ObjectUtils.isEmpty(orderDTO)) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        try {
            lockService.batchLockWithKey(orderDTO.orderItemDTOs().stream().map(OrderItemDTO::productId).collect(Collectors.toList()));
            Map<Long, OrderItemDTO> productQuantityMap = orderDTO.orderItemDTOs().stream().collect(Collectors.toMap(OrderItemDTO::productId, orderItemDTO -> orderItemDTO));
            List<Stock> stocks = stockRepository.findStocksByProductIdIn(new ArrayList<>(productQuantityMap.keySet()));
            for (Stock stock : stocks) {
                if (!ObjectUtils.isEmpty(stock)) {
                    stock.setAvailableQuantity(stock.getAvailableQuantity() + productQuantityMap.get(stock.getProductId()).quantity());
                }
            }
            stockRepository.saveAll(stocks);
        } catch (Exception e) {
            log.error("Error: {}, when updateStockBecauseOfOrderCancellation", e.getLocalizedMessage());
            throw new RuntimeException(e);
        } finally {
            lockService.batchReleaseLock(orderDTO.orderItemDTOs().stream().map(OrderItemDTO::productId).collect(Collectors.toList()));
        }
    }

    @Override
    public void updateStockBecauseOfOrderCreation(OrderDTO orderDTO) {
        if (ObjectUtils.isEmpty(orderDTO)) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        try {
            lockService.batchLockWithKey(orderDTO.orderItemDTOs().stream().map(OrderItemDTO::productId).collect(Collectors.toList()));
            Map<Long, OrderItemDTO> productQuantityMap = orderDTO.orderItemDTOs().stream().collect(Collectors.toMap(OrderItemDTO::productId, orderItemDTO -> orderItemDTO));
            List<Stock> stocks = stockRepository.findStocksByProductIdIn(new ArrayList<>(productQuantityMap.keySet()));
            for (Stock stock : stocks) {
                if (!ObjectUtils.isEmpty(stock) && stock.getAvailableQuantity() >= productQuantityMap.get(stock.getProductId()).quantity()) {
                    stock.setAvailableQuantity(stock.getAvailableQuantity() - productQuantityMap.get(stock.getProductId()).quantity());
                    continue;
                }
                throw new IllegalArgumentException("Stock is not enough for order creation. ProductId: " + stock.getProductId().toString());
            }
            stockRepository.saveAll(stocks);
        } catch (Exception e) {
            log.error("Error: {}, when updateStockBecauseOfOrderCancellation", e.getLocalizedMessage());
            throw new RuntimeException(e);
        } finally {
            lockService.batchReleaseLock(orderDTO.orderItemDTOs().stream().map(OrderItemDTO::productId).collect(Collectors.toList()));
        }
    }

    @Override
    @Transactional
    public void updateStockBecauseOfOrderUpdate(OrderDTO oldOrderDTO, OrderDTO updatedOrderDTO) {
        if (ObjectUtils.isEmpty(oldOrderDTO) || ObjectUtils.isEmpty(updatedOrderDTO)) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        updateStockBecauseOfOrderCancellation(oldOrderDTO);
        updateStockBecauseOfOrderCreation(updatedOrderDTO);
    }

}
