package com.api.shop.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.shop.demo.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {

    Optional<List<Order>> findByUserId(Long userId);

}
