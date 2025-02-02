package com.api.shop.demo.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.shop.demo.model.Cart;
import com.api.shop.demo.model.User;
import com.api.shop.demo.repository.CartRepository;
import com.api.shop.demo.service.cart.CartService;
import com.api.shop.demo.service.user.Userservice;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private Userservice userservice;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    public void createCartToUser(){
        User user = new User();
        user.setId(1L);

        Cart cart = new Cart();
        cart.setId(1L);

        when(this.userservice.getById(1L)).thenReturn(Optional.of(user));
        when(this.cartRepository.save(Mockito.any())).thenReturn(cart);

        Optional<Cart> cartOp = this.cartService.addNewCartToUser(1L);
        
        Assertions.assertTrue(cartOp.isPresent());
        Assertions.assertEquals(1L, cartOp.get().getId());
    }

    @Test
    public void errorToCreateCartUserNotFound(){
        try {
            when(this.userservice.getById(1l)).thenReturn(Optional.empty());
            this.cartService.addNewCartToUser(1L);
        } catch (Exception error) {
            Assertions.assertEquals("Error to create a cart : Error user not found id: 1", error.getMessage());
        }
    }

    @Test
    public void clearCart(){
        Long id = 1L;
        Cart cart = new Cart();
        cart.setId(1L);

        when(this.cartRepository.findById(id)).thenReturn(Optional.of(cart));
        this.cartService.clear(id);
        verify(this.cartRepository).deleteById(id);
    }

    @Test
    public void errorClearCart(){
        try {
            Long id = 1L;
            Cart cart = new Cart();
            cart.setId(1L);
    
            when(this.cartRepository.findById(id)).thenReturn(Optional.empty());
            this.cartService.clear(id);
        } catch (Exception error) {
            Assertions.assertEquals("Error to clean a cart :Error cart not found id :1", error.getMessage());
        }
    }

    @Test
    public void update(){

        Long id = 1L;
        Cart cart = new Cart();
        cart.setId(1L);

        when(this.cartRepository.findById(id)).thenReturn(Optional.of(cart));
        when(this.cartRepository.save(Mockito.any())).thenReturn(cart);

        Optional<Cart> cartOp = this.cartService.update(cart, id);
        Assertions.assertTrue(cartOp.isPresent());
    }


    @Test
    public void errorUpdateCartNotFound(){
        try {
            Long id = 1L;
            Cart cart = new Cart();
            cart.setId(1L);
    
            when(this.cartRepository.findById(id)).thenReturn(Optional.empty());
            this.cartService.update(cart, id);
        } catch (Exception error) {
            Assertions.assertEquals("Error to update a cart :Resource not found cart id: 1", error.getMessage());
        }
  
    }
}
