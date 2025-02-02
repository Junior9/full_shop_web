package com.api.shop.demo.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.shop.demo.dto.OrderDto;
import com.api.shop.demo.enums.OrderStatus;
import com.api.shop.demo.model.Cart;
import com.api.shop.demo.model.CartItem;
import com.api.shop.demo.model.Order;
import com.api.shop.demo.model.OrderItem;
import com.api.shop.demo.model.Product;
import com.api.shop.demo.model.User;
import com.api.shop.demo.repository.OrderRepository;
import com.api.shop.demo.service.cart.CartService;
import com.api.shop.demo.service.order.OrderService;
import com.api.shop.demo.service.user.Userservice;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private Userservice userservice;

    @Mock
    private CartService cartService;

    @Test
    public void placeOrder(){

        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);

        Product product = new Product(); 
        product.setId(1L);
        product.setPrice(BigDecimal.valueOf(10));

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setPrice(product.getPrice());

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setQuantity(1);
        cartItem.setTotalPrice();

        Set<CartItem> items = new HashSet<>();
        items.add(cartItem);
        cart.setItems(items);;

        when(this.userservice.getById(userId)).thenReturn(Optional.of(user));
        when(this.cartService.getByUserId(userId)).thenReturn(Optional.of(cart));
        when(this.orderRepository.save(Mockito.any())).thenReturn(order);

        Optional<Order> orderOp = this.orderService.placeOrder(userId);
        Assertions.assertTrue(orderOp.isPresent());

        verify(this.cartService).clear(cart.getId());
    }

    @Test
    public void errorPlaceOrderUserNotFound(){
        try {
            Long userId = 1L;
            when(this.userservice.getById(userId)).thenReturn(Optional.empty());
            Optional<Order> orderOp = this.orderService.placeOrder(userId);
            Assertions.assertFalse(orderOp.isPresent());
        } catch (Exception error) {
            Assertions.assertEquals("Error to create a new order :Resource not found user id: 1", error.getMessage());
        }
    }


    @Test
    public void errorPlaceOrderCartNotFound(){
        try {
            Long userId = 1L;
            User user = new User();
            user.setId(userId);
            when(this.userservice.getById(userId)).thenReturn(Optional.of(user));
            when(this.cartService.getByUserId(userId)).thenReturn(Optional.empty());

            Optional<Order> orderOp = this.orderService.placeOrder(userId);
            Assertions.assertFalse(orderOp.isPresent());
        } catch (Exception error) {
            Assertions.assertEquals("Error to create a new order :Resource not found cart for the user id: 1", error.getMessage());
        }
    }

    @Test
    public void getOrderById(){
        Long id = 1L;

        User user = new User();
        user.setId(id);
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderId(id);
        order.setTotalAmount(BigDecimal.valueOf(100));
        order.setOrderItems(new HashSet<>());
        order.setUser(user);

        when(this.orderRepository.findById(id)).thenReturn(Optional.of(order));
        
        Optional<OrderDto> orderDtoOp = this.orderService.getOrder(id);
        Assertions.assertTrue(orderDtoOp.isPresent());
    }

    @Test
    public void errorGetOrderByIdValidation(){
        try {
            Long id = 1L;
            User user = new User();
            user.setId(id);
            Order order = new Order();
            order.setOrderDate(LocalDate.now());
            order.setOrderStatus(OrderStatus.PENDING);
            order.setOrderId(id);
            order.setTotalAmount(BigDecimal.valueOf(100));
            order.setOrderItems(new HashSet<>());
    
            when(this.orderRepository.findById(id)).thenReturn(Optional.of(order));
            
            Optional<OrderDto> orderDtoOp = this.orderService.getOrder(id);
            Assertions.assertFalse(orderDtoOp.isPresent());
            
        } catch (Exception error) {
            Assertions.assertEquals("Error to get a order :Error to validate order", error.getMessage());
        }
    }

    @Test
    public void getOrderByUserId(){
        Long id = 1L;

        User user = new User();
        user.setId(id);
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderId(id);
        order.setTotalAmount(BigDecimal.valueOf(100));
        order.setOrderItems(new HashSet<>());
        order.setUser(user);

        when(this.userservice.getById(user.getId())).thenReturn(Optional.of(user));
        when(this.orderRepository.findByUserId(user.getId())).thenReturn(Optional.of(List.of(order)));
        
        Optional<List<OrderDto>> ordersDtoOp = this.orderService.getUserOrders(id);
        Assertions.assertTrue(ordersDtoOp.isPresent());
        Assertions.assertEquals(1, ordersDtoOp.get().size());
    }

    @Test
    public void errorgetOrderByUserIdUserNotFound(){
        try {
            Long id = 1L;
            when(this.userservice.getById(id)).thenReturn(Optional.empty());
            Optional<List<OrderDto>> ordersOp = this.orderService.getUserOrders(id);
            Assertions.assertFalse(ordersOp.isPresent());
        } catch (Exception error) {
            Assertions.assertEquals("Error to get a order :Resource not found cart for the user id: 1", error.getMessage());
        }        
    }

    @Test
    public void errorGetOrderByUserIdOrderNotFound(){
        try {
            Long id = 1L;
            User user = new User();
            user.setId(id);
       
            when(this.userservice.getById(user.getId())).thenReturn(Optional.of(user));
            when(this.orderRepository.findByUserId(user.getId())).thenReturn(Optional.empty());
            
            Optional<List<OrderDto>> ordersDtoOp = this.orderService.getUserOrders(id);
            Assertions.assertFalse(ordersDtoOp.isPresent());
        } catch (Exception error) {
            Assertions.assertEquals("Error to get a order :Orders not found for the user id 1", error.getMessage());
        }
    }

    @Test
    public void deleteOrder() { 
        Long id = 1L;
        Order order = new Order();
        order.setOrderId(id);
        when(this.orderRepository.findById(id)).thenReturn(Optional.of(order));
        this.orderService.deleteById(id);
        verify(this.orderRepository).deleteById(id);
    }

    @Test
    public void errorDeleteOrder() { 
        try {
            Long id = 1L;
            Order order = new Order();
            order.setOrderId(id);
            when(this.orderRepository.findById(id)).thenReturn(Optional.empty());
            this.orderService.deleteById(id);
            verify(this.orderRepository, times(0)).deleteById(id);
        } catch (Exception error) {
            Assertions.assertEquals("Order not found id 1", error.getMessage());
        }
    }

}
