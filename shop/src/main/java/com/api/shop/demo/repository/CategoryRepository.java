package com.api.shop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.shop.demo.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {

    public Category findByName(String name);

}
