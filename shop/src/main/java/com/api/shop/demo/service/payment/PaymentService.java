package com.api.shop.demo.service.payment;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.shop.demo.dto.OrderDto;
import com.api.shop.demo.enums.OrderStatus;
import com.api.shop.demo.exception.PaymentError;
import com.api.shop.demo.exception.ResourceNotFound;
import com.api.shop.demo.exception.UpdateResourceError;
import com.api.shop.demo.model.CardCredit;
import com.api.shop.demo.service.notification.NotificationService;
import com.api.shop.demo.service.order.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService implements PaymentServiceInter {

    private final OrderService orderService;
    private final NotificationService notificationService;
    
    @Override
    @Transactional
    public Optional<Boolean> payment(Long orderId, CardCredit credit) {
        try {
            this.orderService.getOrder(orderId)
                .orElseThrow(()-> new ResourceNotFound("Order not found id " + orderId));

            OrderDto orderDto = this.orderService.updateOderStatus(orderId, OrderStatus.PROCESSING)
                .orElseThrow(()-> new UpdateResourceError("Error to update order id " + orderId));

            this.notificationService.sendEmail(orderDto.getUserId(), "Payment complete for the order " + orderId);
            return Optional.of(Boolean.TRUE);
        } catch (Exception error) {
            throw new PaymentError("Error Payment : " + error.getMessage());
        }
    }

}
