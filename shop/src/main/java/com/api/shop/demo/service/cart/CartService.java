package com.api.shop.demo.service.cart;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.api.shop.demo.exception.CreatedResourceError;
import com.api.shop.demo.exception.DeleteResourceException;
import com.api.shop.demo.exception.ResourceNotFound;
import com.api.shop.demo.exception.UpdateResourceError;
import com.api.shop.demo.model.Cart;
import com.api.shop.demo.model.User;
import com.api.shop.demo.repository.CartRepository;
import com.api.shop.demo.service.user.Userservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CartService implements CartServiceInter{

    private final CartRepository cartRepository;
    private final Userservice userservice;

    @Override
    public Optional<Cart> getById(Long id) {
        try{
            return this.cartRepository.findById(id);
        }catch(Exception error){
            throw new ResourceNotFound(error.getMessage());
        }
    }

    @Override
    public Optional<Cart> addNewCartToUser(Long userId) {
        try{
            Optional<User> userOp = this.userservice.getById(userId);
            if(userOp.isPresent()){
                Cart cart = new Cart();
                cart.setUser(userOp.get());
                Cart cartAdded = this.cartRepository.save(cart);
                return Optional.of(cartAdded);
            }else{
                throw new ResourceNotFound("Error user not found id: " + userId);
            }
        }catch(DataIntegrityViolationException errorV){
            log.error("Error create user : " + errorV.getMessage());
            throw new CreatedResourceError("Error to create a cart : user id " + userId + " already has a cart") ;
        }catch(Exception error){
            log.error("Error create user : " + error.getMessage());
            throw new CreatedResourceError("Error to create a cart : " + error.getMessage() ); 
        }
    }

    @Override
    public void clear(Long id) {
        try{
            Optional<Cart> cartOriginalOp = this.cartRepository.findById(id);
            if(cartOriginalOp.isEmpty()){
                throw new ResourceNotFound("Error cart not found id :" + id); 
            }
            this.delete(id);
        }catch(Exception error){
            throw new DeleteResourceException("Error to clean a cart :" + error.getMessage()); 
        }
    }

    @Override
    public void delete(Long id) {
        try{
            this.cartRepository.deleteById(id);
        }catch(Exception error){
            throw new DeleteResourceException("Error to detele a cart :" + error.getMessage()); 
        }
    }

    @Override
    public Optional<Cart> update(Cart cart, Long cartId) {
        try{
            if(this.cartRepository.findById(cartId).isPresent()){
                Cart cartUpdated = this.cartRepository.save(cart);
                return Optional.of(cartUpdated);
            }else{
                throw new ResourceNotFound("Resource not found cart id: " + cartId); 
            }
        }catch(Exception error){
            throw new UpdateResourceError("Error to update a cart :" + error.getMessage()); 
        }
    }

    @Override
    public Optional<Cart> getByUserId(Long userId) {
        try {
            if(this.userservice.getById(userId).isPresent()){
                Cart cart =   this.cartRepository.findByUserId(userId);
                return Optional.of(cart);
            }else{
                throw new ResourceNotFound("Resource not found user id: " + userId); 
            }
        } catch (Exception error) {
            throw new UpdateResourceError("Error to get a cart by user id :" + error.getMessage()); 
        }
    }

}
