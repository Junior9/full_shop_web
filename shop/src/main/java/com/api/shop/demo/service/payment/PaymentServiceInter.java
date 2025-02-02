package com.api.shop.demo.service.payment;

import java.util.Optional;

import com.api.shop.demo.model.CardCredit;

public interface PaymentServiceInter {

    public Optional<Boolean> payment(Long orderId, CardCredit credit);

}
