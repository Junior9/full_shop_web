package com.api.shop.demo.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.shop.demo.model.Cart;
import com.api.shop.demo.model.CartItem;
import com.api.shop.demo.model.Product;
import com.api.shop.demo.repository.CartItemRepository;
import com.api.shop.demo.service.cart.CartItemService;
import com.api.shop.demo.service.cart.CartService;
import com.api.shop.demo.service.product.ProductService;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceTest {


    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private CartService cartService;
    @Mock
    private ProductService productService;
    @InjectMocks
    private CartItemService cartItemService;


    @Test
    public void addNewItemInTheCart(){

        Long cartId = 1L;
        Long userId = 23L;
        Long productId = 10L;
        int quantity = 2;

        Product product = new Product();
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();

        product.setId(productId);
        product.setPrice(BigDecimal.valueOf(10));

        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        when(this.cartService.getById(cartId)).thenReturn(Optional.of(cart));
        when(this.productService.getById(productId)).thenReturn(Optional.of(product));
        when(this.cartItemRepository.save(Mockito.any())).thenReturn(cartItem);

        Optional<CartItem> cartItemOp = this.cartItemService.addItemToCart(userId, cartId, productId, quantity);

        Assertions.assertTrue(cartItemOp.isPresent());

    }

    @Test
    public void errorToAddNewItemInTheCartProductNotFound(){

        try {
            Long cartId = 1L;
            Long userId = 23L;
            Long productId = 10L;
            int quantity = 2;
            Cart cart = new Cart();
    
            when(this.cartService.getById(cartId)).thenReturn(Optional.of(cart));
            when(this.productService.getById(productId)).thenReturn(Optional.empty());
    
            this.cartItemService.addItemToCart(userId, cartId, productId, quantity);
        } catch (Exception e) {
            Assertions.assertEquals("Resource not found productId 10 cartId 1", e.getMessage());
        }
    }

    @Test
    public void errorToAddNewItemInTheCartCartNotFound(){

        try {
            Long cartId = 1L;
            Long userId = 23L;
            Long productId = 10L;
            int quantity = 2;
            Product product = new Product();
      
            product.setId(productId);
            product.setPrice(BigDecimal.valueOf(10));
    
            when(this.cartService.getById(cartId)).thenReturn(Optional.empty());
            when(this.productService.getById(productId)).thenReturn(Optional.of(product));
    
            this.cartItemService.addItemToCart(userId, cartId, productId, quantity);
        } catch (Exception e) {
            Assertions.assertEquals("Resource not found productId 10 cartId 1", e.getMessage());
        }
    }

    @Test
    public void addExisCartItemInCart(){

        Long cartId = 1L;
        Long userId = 23L;
        Long productId = 10L;
        int quantity = 1;

        Product product = new Product();
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();

        product.setId(productId);
        product.setPrice(BigDecimal.valueOf(10));

        cartItem.setId(1L);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setQuantity(quantity);

        cart.setId(cartId);
        cart.getItems().add(cartItem);

        when(this.cartService.getById(cartId)).thenReturn(Optional.of(cart));
        when(this.productService.getById(productId)).thenReturn(Optional.of(product));
        when(this.cartItemRepository.save(Mockito.any())).thenReturn(cartItem);

        Optional<CartItem> cartItemOp = this.cartItemService.addItemToCart(userId, cartId, productId, quantity);

        Assertions.assertTrue(cartItemOp.isPresent());
        Assertions.assertEquals(2, cartItemOp.get().getQuantity());
    }

    @Test
    public void addTwoDifferentsCartItemInCart(){

        Long cartId = 1L;
        Long userId = 23L;
        Long productId = 10L;
        int quantity = 1;

        Product product = new Product();
        Product productResponse = new Product();
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        CartItem cartItemResponse = new CartItem();

        product.setId(productId);
        product.setPrice(BigDecimal.valueOf(10));

        productResponse.setId(2L);
        productResponse.setName("product2");
        productResponse.setPrice(BigDecimal.valueOf(100));

        cartItem.setId(1L);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setQuantity(quantity);

        cart.setId(cartId);
        cart.getItems().add(cartItem);

        cartItemResponse.setId(2L);
        cartItemResponse.setCart(cart);
        cartItemResponse.setProduct(productResponse);
        cartItemResponse.setUnitPrice(productResponse.getPrice());
        cartItemResponse.setQuantity(quantity);

        when(this.cartService.getById(cartId)).thenReturn(Optional.of(cart));
        when(this.productService.getById(2L)).thenReturn(Optional.of(productResponse));
        when(this.cartItemRepository.save(Mockito.any())).thenReturn(cartItemResponse);

        Optional<CartItem> cartItemOp = this.cartItemService.addItemToCart(userId, cartId, 2L, quantity);

        Assertions.assertTrue(cartItemOp.isPresent());
        Assertions.assertEquals(1, cartItemOp.get().getQuantity());
        Assertions.assertEquals("product2", cartItemOp.get().getProduct().getName());
    }

    @Test
    public void updateQuantityCartItem(){
        Long cartId = 1L;
        Long productId = 10L;
        int quantity = 5;

        Cart cart = new Cart();
        Product product = new Product();
        product.setId(10L);

        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setProduct(product);
        cart.getItems().add(cartItem);

        CartItem cartItemResponse = new CartItem();
        cartItemResponse.setId(1L);
        cartItemResponse.setProduct(product);
        cartItemResponse.setQuantity(quantity);

        when(this.cartService.getById(cartId)).thenReturn(Optional.of(cart));
        when(this.cartItemRepository.save(Mockito.any())).thenReturn(cartItemResponse);

        Optional<CartItem> cartItemOp = this.cartItemService.updateItemQuantity(cartId, productId, quantity);

        Assertions.assertTrue(cartItemOp.isPresent());
        Assertions.assertEquals(5, cartItemOp.get().getQuantity());
    }

    @Test
    public void errorUpdateQuantityCartItemNotFoundInCart(){
        try {
            Long cartId = 1L;
            Long productId = 10L;
            int quantity = 5;    
            Cart cart = new Cart();

            when(this.cartService.getById(cartId)).thenReturn(Optional.of(cart));
            this.cartItemService.updateItemQuantity(cartId, productId, quantity);
        } catch (Exception error) {
            Assertions.assertEquals("Error to update quantity cartItem id 1 product id 10 quantity: 5 Error: Error to get the product id 10 for the cart id 1. Error: Resource not found in cart id 1 the product id 10 does not exist in the cart", error.getMessage());
        }
    }

    @Test
    public void errorUpdateQuantityCartNotFoundInCart(){
        try {
            Long cartId = 1L;
            Long productId = 10L;
            int quantity = 5;    

            when(this.cartService.getById(cartId)).thenReturn(Optional.empty());
            this.cartItemService.updateItemQuantity(cartId, productId, quantity);
        } catch (Exception error) {
            Assertions.assertEquals("Error to update quantity cartItem id 1 product id 10 quantity: 5 Error: Error to get the product id 10 for the cart id 1. Error: Resource not found cart id 1", error.getMessage());
        }
    }

    @Test
    public void deleteItemInTheCart(){
        Long cartId = 1L;
        Long productId = 10L;
        try {
            Product product = new Product();
            product.setId(10L);
            Cart cart = new Cart();
            CartItem cartItem = new CartItem();
            cartItem.setId(cartId);
            cartItem.setProduct(product);
            cartItem.setUnitPrice(BigDecimal.valueOf(2));
            cartItem.setTotalPrice(BigDecimal.valueOf(2));
            cart.setId(cartId);
            cart.getItems().add(cartItem);

            when(this.cartService.getById(cartId)).thenReturn(Optional.of(cart));
            this.cartItemService.deleteItemFromCart(cartId, productId);
            verify(this.cartService).update(cart, cartId);
        } catch (Exception error) {
            Assertions.assertNull(error.getMessage());
        }
    }

    @Test
    public void deleteItemInTheCartItemNotFound(){
        Long cartId = 1L;
        Long productId = 2L;
        try {
            Product product = new Product();
            product.setId(10L);
            Cart cart = new Cart();
            CartItem cartItem = new CartItem();
            cartItem.setId(1L);
            cartItem.setProduct(product);
            cart.setId(1L);
            cart.getItems().add(cartItem);

            when(this.cartService.getById(cartId)).thenReturn(Optional.of(cart));
            this.cartItemService.deleteItemFromCart(cartId, productId);
        } catch (Exception error) {
            Assertions.assertNotNull(error.getMessage());
            Assertions.assertEquals("Error to delete product id 2 for the cart id 1. Error : Resource not found in cart id 1 the product id 2 does not exist in the cart", error.getMessage());
        }
    }

    @Test
    public void deleteItemInTheCartNotFound(){
        Long cartId = 1L;
        Long productId = 2L;
        try {    
            when(this.cartService.getById(cartId)).thenReturn(Optional.empty());
            this.cartItemService.deleteItemFromCart(cartId, productId);
        } catch (Exception error) {
            Assertions.assertNotNull(error.getMessage());
            Assertions.assertEquals("Error to delete product id 2 for the cart id 1. Error : Resource not found cart id 1", error.getMessage());
        }
    }

}
