package com.api.shop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.shop.demo.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
