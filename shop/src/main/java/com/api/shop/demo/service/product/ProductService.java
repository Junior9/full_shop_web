package com.api.shop.demo.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.shop.demo.exception.CreatedResourceError;
import com.api.shop.demo.exception.ResourceNotFound;
import com.api.shop.demo.exception.UpdateResourceError;
import com.api.shop.demo.exception.ValidateResourceError;
import com.api.shop.demo.model.Product;
import com.api.shop.demo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceInter{

    private final ProductRepository productRepository;

    @Override
    public Optional<List<Product>> getAll() {
        try{
            List<Product> products = this.productRepository.findAll();
            return Optional.of(products);
         }catch(Exception error){
             throw new CreatedResourceError("Error to get all products : " +  error.getMessage());
         }
    }

    @Override
    public void deleteById(Long id) {
        try{
           this.productRepository.deleteById(id);
        }catch(Exception error){
            throw new CreatedResourceError("Error to delete a productId : " + id + " " +  error.getMessage());
        }
    }

    @Override
    public Optional<Product> add(Product product) {
        try{
            Product productAdded = this.productRepository.save(product);
            return Optional.of(productAdded);
        }catch(Exception error){
            throw new CreatedResourceError("Error to creare a product : " +  error.getMessage());
        }
    }

    @Override
    public Optional<Product> update(Product product, Long id) {
        try{
            Optional<Product> productOp = this.productRepository.findById(id);
            if(productOp.isEmpty()){
                throw new ResourceNotFound("Error :  product not found id " + id);
            }
            Product productToUpdate = this.updateProductFields(product, productOp.get());
            Product productUpdated = this.productRepository.save(productToUpdate);
            return Optional.of(productUpdated);
        }catch(Exception error){
            throw new UpdateResourceError("Error to update product : "  + error.getMessage());
        }
    }

    @Override
    public Optional<Product> getById(Long id) {
        try{
            Optional<Product> productOp = this.productRepository.findById(id);
            return productOp;
        }catch(Exception error){
            throw new ResourceNotFound("Error to get product id: " + id + " " +  error.getMessage());
        }
    }

    private Product updateProductFields(Product productNew, Product productOrignal) {
        try{
            productOrignal.setBrand(productNew.getBrand());
            productOrignal.setCategory(productNew.getCategory());
            productOrignal.setDescription(productNew.getDescription());
            productOrignal.setInventory(productNew.getInventory());
            productOrignal.setName(productNew.getName());
            productOrignal.setPrice(productNew.getPrice());
            return productOrignal;
        }catch(Exception error){
            throw new ValidateResourceError("Error to validate resource" + error.getMessage());
        }
    }

}
