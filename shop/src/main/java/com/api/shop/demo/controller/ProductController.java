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

import com.api.shop.demo.model.Product;
import com.api.shop.demo.response.ResponseApi;
import com.api.shop.demo.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseApi> add(@RequestBody Product product){
        try{
            Optional<Product> productOp = this.productService.add(product);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(productOp.get()).build());
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error: " + error.getMessage() ).build());
        }
    }

    @PutMapping
    public ResponseEntity<ResponseApi> update(@RequestBody Product product, @PathVariable Long id){
        try{
            Optional<Product> productOp = this.productService.update(product,id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(productOp.get()).build());
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error: " + error.getMessage() ).build());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseApi> getAll(){
        try{
            Optional<List<Product>> productsOp = this.productService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(productsOp.get()).build());
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error: " + error.getMessage() ).build());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseApi> getById(@PathVariable Long id){
        try{
            Optional<Product> productOp = this.productService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(productOp.get()).build());
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error: " + error.getMessage() ).build());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseApi> deleteById(@PathVariable Long id){
        try{
            this.productService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").build());
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error: " + error.getMessage() ).build());
        }
    }

}
