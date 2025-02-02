package com.api.shop.demo.exception;

public class SendEmailError extends RuntimeException {

    public SendEmailError(String message){
        super(message);
    }

}
