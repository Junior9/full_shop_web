package com.api.shop.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.api.shop.demo.model.OrderItem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String status;
    private Set<OrderItem> items;

}
