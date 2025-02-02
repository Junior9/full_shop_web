package com.api.shop.demo.service.imagine;

import java.sql.Blob;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.shop.demo.exception.CreatedResourceError;
import com.api.shop.demo.exception.DeleteResourceException;
import com.api.shop.demo.exception.ResourceNotFound;
import com.api.shop.demo.model.Imagine;
import com.api.shop.demo.model.Product;
import com.api.shop.demo.repository.ImagineRepository;
import com.api.shop.demo.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImagineService implements ImagineServiceInter {

    private final ImagineRepository imagineRepository;
    private final ProductService productService;

    @Override
    public Optional<Imagine> add(MultipartFile file, Long productId) {
        try {
            Optional<Product> productOp = this.productService.getById(productId);
            if(productOp.isPresent()){
                Imagine imagine = new Imagine();
                imagine.setName(file.getOriginalFilename());
                imagine.setProduct(productOp.get());
                imagine.setType(file.getContentType());
                imagine.setDownload("/api/imagine/upload/");
                imagine.setImagine(new SerialBlob(file.getBytes()));

                Imagine imagineAdded = this.imagineRepository.save(imagine);
                imagineAdded.setDownload(imagineAdded.getDownload() + imagineAdded.getId());
                this.imagineRepository.save(imagineAdded);
                return Optional.of(imagineAdded);
            }else{
                throw new ResourceNotFound("Error resource not found product id " + productId);
            }
        } catch (Exception error) {
            throw new CreatedResourceError("Error to create resource imageine for the product id " + productId + ". Error " + error.getMessage());
        }
    }

    @Override
    public Optional<Imagine> getImageById(Long id) {
        try {
            Optional<Imagine> imagineOp = this.imagineRepository.findById(id);
            if(imagineOp.isPresent()){
                return imagineOp;
            }else{
                throw new ResourceNotFound("Error resource not found iimagine id " + id);
            }
        } catch (Exception error) {
            throw new CreatedResourceError("Error to get imagine id " + id + " Error : " + error.getMessage());
        }
    }

    @Override
    public void deleteImageById(Long id) {
        try {
            this.imagineRepository.deleteById(id);
        } catch (Exception error) {
            throw new DeleteResourceException("Error to delete imagine id " + id + " Error : " + error.getMessage());
        }

    }

}
