package com.api.shop.demo.service.cart;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.shop.demo.exception.DeleteResourceException;
import com.api.shop.demo.exception.GetResourceError;
import com.api.shop.demo.exception.ResourceNotFound;
import com.api.shop.demo.exception.UpdateResourceError;
import com.api.shop.demo.model.Cart;
import com.api.shop.demo.model.CartItem;
import com.api.shop.demo.model.Product;
import com.api.shop.demo.repository.CartItemRepository;
import com.api.shop.demo.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartItemService implements CartItemServiceInter {

    private final CartItemRepository cartItemRepository; 
    private final ProductService productService;
    private final CartService cartService;

    @Override
    @Transactional
    public Optional<CartItem> addItemToCart(Long userId, Long cartId, Long productId, int quantity) {

        try{
            Optional<Product> productOp = this.productService.getById(productId);
            Optional<Cart> cartOp = this.cartService.getById(cartId);
            if(!productOp.isPresent() || !cartOp.isPresent()){
                throw new ResourceNotFound("Resource not found productId " + productId + " cartId " + cartId );
            }
            
            Product product = productOp.get();
            Cart cart = cartOp.get();

            BigDecimal cartAmount = cart.getTotalAmount();

            CartItem cartItem = cart.getItems().stream()
                .filter(c -> c.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());

            if(Objects.nonNull(cartItem.getId())){
                 cartItem.setQuantity(cartItem.getQuantity() +  quantity);
                 cartAmount = cartAmount.add( cartItem.getUnitPrice().multiply(BigDecimal.valueOf(quantity)) );
            }else{
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setUnitPrice(product.getPrice());
                cartItem.setTotalPrice();

                BigDecimal totalItemPrice = cartItem.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
                cartAmount = cartAmount.add(totalItemPrice);
            }
            cart.setTotalAmount(cartAmount);
            CartItem cartItemAdded = this.cartItemRepository.save(cartItem);
            this.cartService.update(cart, cartId);

            return Optional.of(cartItemAdded);
        }catch(Exception error){
            throw new ResourceNotFound(error.getMessage());
        }
    }

    @Override
    public Optional<CartItem> updateItemQuantity(Long cartId, Long productId, int quantity) {

        try {
            Optional<CartItem> cartItemOp = this.getItemCart(cartId, productId);
            CartItem cartItem = cartItemOp.get();
            cartItem.setQuantity(quantity);
            CartItem cartItemOpUpdated = this.cartItemRepository.save(cartItem);
            return Optional.of(cartItemOpUpdated);
        } catch (Exception error) {
            throw new UpdateResourceError("Error to update quantity cartItem id " + cartId + " product id " + productId + " quantity: " + quantity + " Error: " + error.getMessage()  );
        }
    }

    @Override
    public void deleteItemFromCart(Long cartId, Long productId) {
        try{
            Optional<Cart> cartOp = this.cartService.getById(cartId);
            if(cartOp.isPresent()){
                Cart cart = cartOp.get();

                Optional<CartItem> itemOp = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
                if(itemOp.isPresent()){
                    BigDecimal cartToatalAmount = cart.getTotalAmount();
                    cartToatalAmount = cartToatalAmount.subtract(itemOp.get().getTotalPrice());
                    cart.setTotalAmount(cartToatalAmount);
                    cart.removeItem(itemOp.get());
                    this.cartService.update(cart, cartId);
                }else{
                    throw new ResourceNotFound("Resource not found in cart id " + cartId + " the product id " + productId + " does not exist in the cart");
                }
            }else{
                throw new ResourceNotFound("Resource not found cart id " + cartId);
            }
        }catch(Exception error){
            throw new DeleteResourceException("Error to delete product id " + productId + " for the cart id " + cartId + ". Error : " + error.getMessage())  ;
        }
    }

    @Override
    public Optional<CartItem> getItemCart(Long cartId, Long productId) {
        try{
            Optional<Cart> cartOp = this.cartService.getById(cartId);
            if(cartOp.isPresent()){
                Cart cart = cartOp.get();
                Optional<CartItem> itemOp = cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();

                if(itemOp.isPresent()){
                    return itemOp; 
                }else{
                    throw new ResourceNotFound("Resource not found in cart id " + cartId + " the product id " + productId + " does not exist in the cart");
                }
            }else{
                throw new ResourceNotFound("Resource not found cart id " + cartId );
            }
        }catch(Exception error){
            throw new GetResourceError("Error to get the product id " + productId + " for the cart id " + cartId + ". Error: " + error.getMessage());
        }

    }

}
