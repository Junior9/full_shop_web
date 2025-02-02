package com.api.shop.demo.service.imagine;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.api.shop.demo.model.Imagine;

public interface ImagineServiceInter {

    public Optional<Imagine> add(MultipartFile file, Long productId);
    public Optional<Imagine> getImageById(Long id);
    public void deleteImageById(Long id);

}
