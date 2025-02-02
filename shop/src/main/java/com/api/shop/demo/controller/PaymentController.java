package com.api.shop.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.shop.demo.request.PaymentRequest;
import com.api.shop.demo.response.ResponseApi;
import com.api.shop.demo.service.payment.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payment/")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("")
    public ResponseEntity<ResponseApi> add(@RequestBody PaymentRequest paymentRequest){
        try{
            this.paymentService.payment(paymentRequest.getOrderId(), paymentRequest.getCardCredit());
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").build());
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error: " + error.getMessage() ).build());
        }
    }
}
