package com.api.shop.demo.service.product;

import java.util.List;
import java.util.Optional;

import com.api.shop.demo.model.Product;
import com.api.shop.demo.model.User;

public interface ProductServiceInter {

    public Optional<List<Product>> getAll();
    public Optional<User> add(User user);
    public Optional<User> update(User user);
    public Optional<User> getById(String id);
    public Optional<User> getuserByEmailAndPassword(String email, String password);
    public void deleteById(String id);
    
}
