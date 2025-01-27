package com.api.shop.demo.service.category;

import java.util.Optional;

import com.api.shop.demo.model.Category;

import java.util.List;

public interface CategoryServiceInter {

    public Optional<List<Category>> getAll();
    public Optional<Category> getByName(String name);
    public Optional<Category> getById(String id);
    public void deleteById(String id);
    public Optional<Category> add(Category category);
    public Optional<Category> update(Category category, String id);

}
