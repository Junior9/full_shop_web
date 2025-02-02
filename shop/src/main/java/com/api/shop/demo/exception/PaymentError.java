package com.api.shop.demo.exception;

public class PaymentError extends RuntimeException {
    public PaymentError(String message){
        super(message);
    }
}
