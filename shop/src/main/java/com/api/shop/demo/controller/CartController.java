package com.api.shop.demo.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.shop.demo.model.Cart;
import com.api.shop.demo.response.ResponseApi;
import com.api.shop.demo.service.cart.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart/")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("{userId}")
    public ResponseEntity<ResponseApi> addCartToUser(@PathVariable Long userId){
        try{
            Optional<Cart> cartOp = this.cartService.addNewCartToUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(cartOp.get()).build());
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error : " + error.getMessage() ).build());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseApi> getById(@PathVariable Long id){
        try{
            Optional<Cart> cartOp = this.cartService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(cartOp.get()).build()); 
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error : " + error.getMessage()).build());
        }
    }

    @GetMapping("/clear/{id}")
    public ResponseEntity<ResponseApi> clear(@PathVariable Long id){
        try{
            this.cartService.clear(id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").build()); 
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error : " + error.getMessage()).build());
        }
    }

}
