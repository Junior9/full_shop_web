package com.api.shop.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.shop.demo.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByUserId(Long userId);
    
}
