package com.api.shop.demo.service.user;

import java.util.List;
import java.util.Optional;

import com.api.shop.demo.model.User;

public interface UserServiceInter {
    
    public Optional<List<User>> getAll();
    public Optional<User> add(User user);
    public Optional<User> update(User user);
    public Optional<User> getById(Long id);
    public Optional<User> getuserByEmailAndPassword(String email, String password);
    public void deleteById(Long id);
}
