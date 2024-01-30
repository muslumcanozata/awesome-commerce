package com.commerce.order.mapper;

import com.commerce.order.model.dto.OrderDTO;
import com.commerce.order.model.dto.OrderItemDTO;
import com.commerce.order.model.entity.Order;
import com.commerce.order.model.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CoreMapper {
    CoreMapper INSTANCE = Mappers.getMapper( CoreMapper.class );

    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);
    OrderItem orderItemDTOToOrderItem(OrderItemDTO orderItemDTO);
    List<OrderItem> orderItemDTOsToOrderItems(List<OrderItemDTO> orderItemDTOs);
    List<OrderItemDTO> orderItemsToOrderItemDTOs(List<OrderItem> orderItems);
    List<OrderDTO> ordersToOrderDTOs(List<Order> orders);
    OrderDTO orderToOrderDTO(Order order);
    Order orderDTOToOrder(OrderDTO orderDTO);
}
