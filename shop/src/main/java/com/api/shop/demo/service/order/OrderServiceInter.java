package com.api.shop.demo.service.order;

import java.util.List;
import java.util.Optional;

import com.api.shop.demo.dto.OrderDto;
import com.api.shop.demo.enums.OrderStatus;
import com.api.shop.demo.model.Order;

public interface OrderServiceInter{
    Optional<Order> placeOrder(Long userId);
    Optional<OrderDto> getOrder(Long orderId);
    Optional<List<OrderDto>> getUserOrders(Long userId);
    Optional<List<Order>> allOrders();
    OrderDto convertToDto(Order order);
    void deleteById(Long id);
    Optional<OrderDto> updateOderStatus(Long orderId, OrderStatus status); 
}
