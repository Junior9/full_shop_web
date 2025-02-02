package com.api.shop.demo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestNewCartItem {
    private Long userId;
    private Long productId;
    private Long cartId;
    private int quantity;
}
