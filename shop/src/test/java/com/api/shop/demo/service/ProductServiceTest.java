package com.api.shop.demo.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.shop.demo.model.Category;
import com.api.shop.demo.model.Product;
import com.api.shop.demo.repository.ProductRepository;
import com.api.shop.demo.service.product.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void createProduct(){
        Product product = new Product();
        product.setName("Product test");
        product.setDescription("Description test");
        product.setBrand("Brand test");
        product.setCategory(new Category("Eletronic test"));
        product.setPrice(BigDecimal.valueOf(122.00));

        Product productReposnse = new Product();
        productReposnse.setName("Product test");
        productReposnse.setDescription("Description test");
        productReposnse.setBrand("Brand test");
        productReposnse.setCategory(new Category("Eletronic test"));
        productReposnse.setPrice(BigDecimal.valueOf(122.00));
        productReposnse.setId(1L);

        when(this.productRepository.save(product)).thenReturn(productReposnse);
        Optional<Product> productOp = this.productService.add(product);

        Assertions.assertTrue(productOp.isPresent());
        Assertions.assertEquals(1L, productOp.get().getId());
    }

    @Test
    public void errorCreateProduct(){
       try {
            Product product = new Product();
            product.setName("Product test");
            product.setDescription("Description test");
            product.setBrand("Brand test");
            product.setCategory(new Category("Eletronic test"));
            product.setPrice(BigDecimal.valueOf(122.00));

            when(this.productRepository.save(product)).thenReturn(null);
            this.productService.add(product);
       } catch (Exception error) {
            Assertions.assertEquals("Error to creare a product : null", error.getMessage());
       }
    }

    @Test
    public void getAllProducts(){
        Product product = new Product();
        product.setName("Product test");
        product.setDescription("Description test");
        product.setBrand("Brand test");
        product.setCategory(new Category("Eletronic test"));
        product.setPrice(BigDecimal.valueOf(122.00));
        when(this.productRepository.findAll()).thenReturn(List.of(product));
        Optional<List<Product>> productsOp = this.productService.getAll();
        Assertions.assertTrue(productsOp.isPresent());
        Assertions.assertEquals(1, productsOp.get().size());
    }


    @Test
    public void updateCart(){
        Long id = 1L;
        Product product = new Product();
        product.setId(1L);
        product.setName("Computer");
        when(this.productRepository.findById(id)).thenReturn(Optional.of(product));
        when(this.productRepository.save(Mockito.any())).thenReturn(product);
        Optional<Product> productOp = this.productService.update(product, id);

        Assertions.assertTrue(productOp.isPresent());
        Assertions.assertEquals("Computer", productOp.get().getName());
    }

    @Test
    public void erroUpdateCartProductNotFound(){
        try {
            Long id = 1L;
            Product product = new Product();
            product.setId(1L);
            product.setName("Computer");
            when(this.productRepository.findById(id)).thenReturn(Optional.empty());
            this.productService.update(product, id);
        } catch (Exception error) {
            Assertions.assertEquals("Error to update product : Error :  product not found id 1", error.getMessage());
        }
    }

    @Test
    public void getByIdTest(){
        Long id = 1L;
        Product product = new Product();
        product.setId(1L);
        product.setName("Computer");
        when(this.productRepository.findById(id)).thenReturn(Optional.of(product));
        Optional<Product> productOp = this.productService.getById(id);
        Assertions.assertTrue(productOp.isPresent());
    }


}
