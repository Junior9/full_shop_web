package com.api.shop.demo.service.cart;

import java.util.Optional;

import com.api.shop.demo.model.CartItem;

public interface CartItemServiceInter {
    public Optional<CartItem> addItemToCart(Long userId, Long cartId, Long productId, int quantity);
    public Optional<CartItem> updateItemQuantity(Long cartId, Long productId, int quantity);
    public void deleteItemFromCart(Long cartId, Long productId);
    public Optional<CartItem> getItemCart(Long cartId, Long productId);
}
