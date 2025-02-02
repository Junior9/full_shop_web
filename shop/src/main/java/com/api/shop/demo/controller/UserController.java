package com.api.shop.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.shop.demo.model.User;
import com.api.shop.demo.response.ResponseApi;
import com.api.shop.demo.service.user.Userservice;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {

    private final Userservice userservice;

    @GetMapping("all")
    public ResponseEntity<ResponseApi> getAll(){
        try{
            Optional<List<User>> usersOp = this.userservice.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(usersOp.get()).build());
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error: " + error.getMessage() ).build());
        }
    }

    @PostMapping
    public ResponseEntity<ResponseApi> add(@RequestBody User user){
        try{
            Optional<User> userOp = this.userservice.add(user);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Success").data(userOp.get()).build());
        }catch(Exception error){
            return ResponseEntity.status(HttpStatus.OK).body(ResponseApi.builder().message("Error: " + error.getMessage() ).build());
        }
    }

}
