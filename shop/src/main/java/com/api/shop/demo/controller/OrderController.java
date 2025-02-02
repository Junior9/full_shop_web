package com.api.shop.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.shop.demo.dto.OrderDto;
import com.api.shop.demo.model.Order;
import com.api.shop.demo.response.ResponseApi;
import com.api.shop.demo.service.order.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("{userId}")
    public ResponseEntity<ResponseApi> createOrderToUser(@PathVariable Long userId){
        try {
            Optional<Order> orderOp = this.orderService.placeOrder(userId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success: ").data(orderOp.get()).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error: " + error.getMessage() ).build());
        }
    }

    @GetMapping("{orderId}")
    public ResponseEntity<ResponseApi> getOrderById(@PathVariable Long orderId){
        try {
            Optional<OrderDto> orderOp = this.orderService.getOrder(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success: ").data(orderOp.get()).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error: " + error.getMessage() ).build());
        }
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<ResponseApi> getUserOrders(@PathVariable Long userId){
        try {
            Optional<List<OrderDto>> ordersOp = this.orderService.getUserOrders(userId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success: ").data(ordersOp.get()).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error: " + error.getMessage() ).build());
        }
    }

    @GetMapping("/all/orders")
    public ResponseEntity<ResponseApi> getAll(){
        try {
            Optional<List<Order>> ordersOp = this.orderService.allOrders();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success: ").data(ordersOp.get()).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error: " + error.getMessage() ).build());
        }
    }

    @DeleteMapping("{orderId}")
    public ResponseEntity<ResponseApi> deleteById(@PathVariable Long orderId){
        try {
            this.orderService.deleteById(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success: ").build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to delete order: " + error.getMessage() ).build());
        }
    }
}
