package com.api.shop.demo.request;

import com.api.shop.demo.model.CardCredit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequest {
    private Long orderId;
    private CardCredit cardCredit;
}
