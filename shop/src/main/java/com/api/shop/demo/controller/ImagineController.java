package com.api.shop.demo.controller;

import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.shop.demo.model.Imagine;
import com.api.shop.demo.response.ResponseApi;
import com.api.shop.demo.service.imagine.ImagineService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/imagine/")
@RequiredArgsConstructor
public class ImagineController {

    private final ImagineService imagineService;

    @PostMapping("{productId}")
    public ResponseEntity<ResponseApi> add(@RequestBody MultipartFile imagine, @PathVariable Long productId){
        try {
            Optional<Imagine> imagineAdded = this.imagineService.add(imagine, productId);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseApi.builder().message("Success").data(imagineAdded.get()).build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message(error.getMessage()).build());
        }
    }

    @GetMapping("upload/{imagineId}")
    public ResponseEntity<Resource> downloadImagine(@PathVariable Long imagineId){
        try {
            Optional<Imagine> imagineOp = this.imagineService.getImageById(imagineId);
            Imagine image = imagineOp.get();
            ByteArrayResource resource = new ByteArrayResource(image.getImagine().getBytes(1, (int) image.getImagine().length()));
            return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.parseMediaType("image/jpeg"))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getName()  + "\"")
            .body(resource);
        
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseApi> deleteById(@PathVariable Long id){
        try {
            this.imagineService.deleteImageById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseApi.builder().message("Success").build());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message(error.getMessage()).build());
        }
    }

}
