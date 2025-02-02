package com.api.shop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.shop.demo.model.User;

public interface UserRepository extends JpaRepository<User,Long> {

}
