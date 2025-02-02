package com.api.shop.demo.service;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.api.shop.demo.model.Imagine;
import com.api.shop.demo.model.Product;
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
