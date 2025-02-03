package com.api.shop.demo.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.api.shop.demo.model.User;

public interface UserServiceInter extends UserDetailsService{
    
    public Optional<List<User>> getAll();
    public Optional<User> addCustomer(User user);
    public Optional<User> update(User user);
    public Optional<User> getById(Long id);
    public Optional<User> getuserByEmailAndPassword(String email, String password);
    public void deleteById(Long id);
}
