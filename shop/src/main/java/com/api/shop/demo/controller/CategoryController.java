package com.api.shop.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.shop.demo.model.Category;
import com.api.shop.demo.response.ResponseApi;
import com.api.shop.demo.service.category.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/category/")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<ResponseApi> add(@RequestBody Category category){
        try{
            Optional<Category> categoryOp = this.categoryService.add(category);
            if(categoryOp.isPresent()){
                return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(categoryOp.get()).build());
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to create category").build());
            }
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message(error.getMessage()).build());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseApi> update(@RequestBody Category category, @PathVariable String id){
        try{
            Optional<Category> categoryOp = this.categoryService.update(category, id);
            if(categoryOp.isPresent()){
                return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(categoryOp.get()).build());
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to update category").build());
            }
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message(error.getMessage()).build());
        }
    }

    @GetMapping("all")
    public ResponseEntity<ResponseApi> getAll(){
        try{
            Optional<List<Category>> categoriesOp = this.categoryService.getAll();
            if(categoriesOp.isPresent()){
                return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(categoriesOp.get()).build());
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to get categories").build());
            }
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message(error.getMessage()).build());
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<ResponseApi> getById(@PathVariable String id){

        try{
            Optional<Category> categoryOp = this.categoryService.getById(id);
            if(categoryOp.isPresent()){
                return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(categoryOp.get()).build());
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to get category id " + id).build());
            }
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message(error.getMessage()).build());
        }
    }

    @GetMapping("name/{name}")
    public ResponseEntity<ResponseApi> getByName(@PathVariable String name){

        try{
            Optional<Category> categoryOp = this.categoryService.getByName(name);
            if(categoryOp.isPresent()){
                return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(categoryOp.get()).build());
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error to get category name " + name).build());
            }
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message(error.getMessage()).build());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseApi> deleteById(@PathVariable String id){
        try{
            this.categoryService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").build());
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message(error.getMessage()).build());
        }
    }

}
