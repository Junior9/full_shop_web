package com.api.shop.demo.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.shop.demo.dto.OrderDto;
import com.api.shop.demo.enums.OrderStatus;
import com.api.shop.demo.model.CardCredit;
import com.api.shop.demo.service.notification.NotificationService;
import com.api.shop.demo.service.order.OrderService;
import com.api.shop.demo.service.payment.PaymentService;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private OrderService orderService;

    @Mock
    private NotificationService notificationService;

    @Test
    public void paymentTest(){
        CardCredit cardCredit = CardCredit.builder().build();
        Long orderId = 1L;
        
        OrderDto orderDto =  OrderDto.builder().userId(1L).build();
        when(this.orderService.getOrder(orderId)).thenReturn(Optional.of(orderDto));
        when(this.orderService.updateOderStatus(orderId, OrderStatus.PROCESSING)).thenReturn(Optional.of(orderDto));
        when(this.notificationService.sendEmail(Mockito.anyLong(), Mockito.anyString())).thenReturn(Optional.of(Boolean.TRUE));

        Optional<Boolean> response = this.paymentService.payment(orderId, cardCredit);
        Assertions.assertTrue(response.get());
    }

    @Test
    public void errorPaymentTestOrderNotFound(){
        try {
            CardCredit cardCredit = CardCredit.builder().build();
            Long orderId = 1L;
            when(this.orderService.getOrder(orderId)).thenReturn(Optional.empty());
            Optional<Boolean> response = this.paymentService.payment(orderId, cardCredit);
            Assertions.assertFalse(response.get());
        } catch (Exception error) {
            Assertions.assertEquals("Error Payment : Order not found id 1", error.getMessage());
        }
    }

    @Test
    public void errorPaymentTestOrderNotUpdate(){
        try {
            CardCredit cardCredit = CardCredit.builder().build();
            Long orderId = 1L;
            
            OrderDto orderDto =  OrderDto.builder().userId(1L).build();
            when(this.orderService.getOrder(orderId)).thenReturn(Optional.of(orderDto));
            when(this.orderService.updateOderStatus(orderId, OrderStatus.PROCESSING)).thenReturn(Optional.empty());
    
            Optional<Boolean> response = this.paymentService.payment(orderId, cardCredit);
            Assertions.assertFalse(response.get());
        } catch (Exception error) {
            Assertions.assertEquals("Error Payment : Error to update order id 1", error.getMessage());
        }
    }

}
