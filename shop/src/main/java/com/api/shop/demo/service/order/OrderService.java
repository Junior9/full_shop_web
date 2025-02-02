package com.api.shop.demo.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.api.shop.demo.dto.OrderDto;
import com.api.shop.demo.enums.OrderStatus;
import com.api.shop.demo.exception.CreatedResourceError;
import com.api.shop.demo.exception.GetResourceError;
import com.api.shop.demo.exception.ResourceNotFound;
import com.api.shop.demo.exception.UpdateResourceError;
import com.api.shop.demo.exception.ValidateResourceError;
import com.api.shop.demo.model.Cart;
import com.api.shop.demo.model.Order;
import com.api.shop.demo.model.OrderItem;
import com.api.shop.demo.model.User;
import com.api.shop.demo.repository.OrderRepository;
import com.api.shop.demo.service.cart.CartService;
import com.api.shop.demo.service.user.Userservice;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService implements OrderServiceInter {

    private final Userservice userservice;
    private final CartService cartService;
    private final OrderRepository orderRepository;

    @Override
    public  Optional<Order> placeOrder(Long userId) {
        try{
            Optional<User> userOp = this.userservice.getById(userId);
            if(userOp.isPresent()){
                Optional<Cart> cartOp = this.cartService.getByUserId(userId);
                if(cartOp.isPresent()){
                    Order order = new Order();
                    order.setOrderStatus(OrderStatus.PENDING);
                    order.setUser(userOp.get());
                    order.setOrderDate(LocalDate.now());
                    
                    Order orderAdded = this.orderRepository.save(order);
    
                    Set<OrderItem> orderItems = cartOp.get().getItems()
                        .stream()
                        .map(itemCart -> new OrderItem(orderAdded, itemCart.getProduct(), itemCart.getQuantity(), itemCart.getTotalPrice()))
                        .collect(Collectors.toSet());
                    
                    BigDecimal totalOrderAmount = orderItems
                        .stream()
                        .map(item -> item.getPrice())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
    
                    order.setTotalAmount(totalOrderAmount);
                    order.setOrderItems(orderItems);
                    Order orderUpdated = this.orderRepository.save(order);

                    this.cartService.clear(cartOp.get().getId());
    
                    return Optional.of(orderUpdated);
                }else{
                    throw new ResourceNotFound("Resource not found cart for the user id: " + userId); 
                }
            }else{
                  throw new ResourceNotFound("Resource not found user id: " + userId); 
            }
        }catch(Exception error){
            throw new CreatedResourceError("Error to create a new order :" + error.getMessage());
        }
    }

    @Override
    public  Optional<OrderDto> getOrder(Long orderId) {
        try {
            Optional<Order> orderOp = this.orderRepository.findById(orderId);
            if(orderOp.isPresent()){
                OrderDto orderDto = this.convertToDto(orderOp.get());
                return Optional.of(orderDto);
            }else{
                throw new ResourceNotFound("Order not found id " + orderId);
            }
        } catch (Exception error) {
            throw new GetResourceError("Error to get a order :" + error.getMessage());
        }
    }

    @Override
    public  Optional<List<OrderDto>> getUserOrders(Long userId) {
        try {
            Optional<User> userOp = this.userservice.getById(userId);
            if(userOp.isPresent()){
                Optional<List<Order>> orderOp = this.orderRepository.findByUserId(userId);
                if(orderOp.isPresent()){
                    List<Order> orders = orderOp.get();
                    List<OrderDto> orderDtos = orders.stream()
                        .map(order -> this.convertToDto(order))
                        .toList();

                    return Optional.of(orderDtos);
                }else{
                    throw new ResourceNotFound("Orders not found for the user id " + userId);
                }
            }else{
                throw new ResourceNotFound("Resource not found cart for the user id: " + userId); 
            }
        } catch (Exception error) {
            throw new GetResourceError("Error to get a order :" + error.getMessage());
        }
    }

    @Override
    public OrderDto convertToDto(Order order) {
        try {
            return OrderDto.builder()
            .id(order.getOrderId())
            .userId(order.getUser().getId())
            .orderDate(order.getOrderDate())
            .totalAmount(order.getTotalAmount())
            .status(order.getOrderStatus().toString())
            .items(order.getOrderItems())
            .build();
        } catch (Exception error) {
            throw new ValidateResourceError("Error to validate order");
        }  
    }

    @Override
    public void deleteById(Long id) {
        try {
            Optional<Order> orderOp = this.orderRepository.findById(id);
            if(orderOp.isPresent()){
                this.orderRepository.deleteById(id);
            }else{
                throw new ResourceNotFound("Order not found id " + id);
            }
        } catch (Exception error) {
            throw new ValidateResourceError(error.getMessage());
        }
    }

    @Override
    public Optional<OrderDto> updateOderStatus(Long orderId, OrderStatus status) {
        try {
            Optional<Order> orderOp = this.orderRepository.findById(orderId);
            if(orderOp.isPresent()){
                Order order = orderOp.get();
                order.setOrderStatus(status);
                Order orderUpdated = this.orderRepository.save(order);
                return Optional.of(this.convertToDto(orderUpdated));
            }else{
                throw new ResourceNotFound("Order not found id " + orderId);
            }
        } catch (Exception error) {
            throw new UpdateResourceError("Error to update order status" + error.getMessage());
        }
    }

    @Override
    public Optional<List<Order>> allOrders() {
        try {
            List<Order> orders = this.orderRepository.findAll();
            return Optional.of(orders);
        } catch (Exception error) {
            throw new GetResourceError("Error to get all orders: " + error.getMessage());
        }
    }

}
