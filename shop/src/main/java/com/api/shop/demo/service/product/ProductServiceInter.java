package com.api.shop.demo.service.product;

import java.util.List;
import java.util.Optional;

import com.api.shop.demo.model.Product;

public interface ProductServiceInter {

    public Optional<List<Product>> getAll();
    public Optional<Product> add(Product product);
    public Optional<Product> update(Product product, Long id);
    public Optional<Product> getById(Long id);
    public void deleteById(Long id);
    
}
