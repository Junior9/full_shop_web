package com.api.shop.demo.service.cart;

import java.util.Optional;

import com.api.shop.demo.model.Cart;

public interface CartServiceInter {

    public Optional<Cart> addNewCartToUser(Long userId);
    public Optional<Cart> update(Cart cart ,Long cartId);
    public void clear(Long id);
    public void delete(Long id);
    public Optional<Cart> getById(Long id);
    public Optional<Cart> getByUserId(Long userId);


}
