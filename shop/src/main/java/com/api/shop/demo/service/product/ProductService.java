package com.api.shop.demo.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.shop.demo.model.Product;
import com.api.shop.demo.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceInter{

    @Override
    public Optional<List<Product>> getAll() {
        List<Product> products = List.of(new Product());
        return Optional.of(products);
    }

    @Override
    public Optional<User> add(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public Optional<User> update(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Optional<User> getById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public Optional<User> getuserByEmailAndPassword(String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getuserByEmailAndPassword'");
    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

}
