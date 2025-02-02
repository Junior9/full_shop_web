package com.api.shop.demo.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.shop.demo.model.CartItem;
import com.api.shop.demo.request.RequestNewCartItem;
import com.api.shop.demo.response.ResponseApi;
import com.api.shop.demo.service.cart.CartItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart-item/")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("")
    public ResponseEntity<ResponseApi> add(@RequestBody RequestNewCartItem request){
        try {
            Optional<CartItem> cartItemOp = this.cartItemService
                .addItemToCart(request.getUserId(), request.getCartId(), request.getProductId(), request.getQuantity());
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(cartItemOp.get()).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to create cartItem : " +error.getMessage()).build());
        }
    }

    @PostMapping("update/quantity/{cartId}/{productId}/{quantity}")
    public ResponseEntity<ResponseApi> updateCartItemQuantity(@PathVariable String cartId,@PathVariable String productId,@PathVariable int quantity){
        try {
            Optional<CartItem> cartItemOp = this.cartItemService.updateItemQuantity(Long.parseLong(cartId), Long.parseLong(productId), quantity);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(cartItemOp.get()).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to update quantity : " +error.getMessage()).build());
        }
    }

    @DeleteMapping("delete/{cartId}/{productId}/")
    public ResponseEntity<ResponseApi> deleteItemTocart(@PathVariable String cartId,@PathVariable String productId){
        try {
            this.cartItemService.deleteItemFromCart(Long.parseLong(cartId),Long.parseLong(productId)); 
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to delete cartItem : " +error.getMessage()).build());
        }
    }

}
