package com.api.shop.demo.service;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



import com.api.shop.demo.repository.ImagineRepository;
import com.api.shop.demo.service.imagine.ImagineService;
import com.api.shop.demo.service.product.ProductService;

@ExtendWith(MockitoExtension.class)
public class ImagineServiceTest {

    @InjectMocks
    private ImagineService imagineService;

    @Mock
    private ImagineRepository imagineRepository;

    @Mock
    private ProductService productService;


}
