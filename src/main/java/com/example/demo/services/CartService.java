
package com.example.demo.services;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.CartDTO;
import com.example.demo.Repository.CartItemsRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.model.CartItemsModel;
import com.example.demo.model.CartModel;
import com.example.demo.model.FoodModel;
import com.example.demo.model.UserModel;


@Service
public class CartService {
    private CartRepository cartRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public CartService(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }

    public CartModel getOrCreateCart(String userId) {

        return cartRepository.findByUserId(userId)
            .orElseGet(() -> {
                CartModel newCart = new CartModel();
                newCart.generateId();
                newCart.setUserId(userId);
                newCart.setCreatedAt();
                newCart.setUpdatedAt();
                return cartRepository.save(newCart);
            });
    }

    public CartDTO getCartDetails(String userId) {

        // Step 1: find cart
        CartModel cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart == null) return null;
    
        // Step 2: get user
        UserModel user = mongoTemplate.findById(cart.getUserId(), UserModel.class);
    
        // Step 3: get cart items
        List<CartItemsModel> items = cartItemsRepository.findByCartId(cart.getId());
    
        // Step 4: build item DTOs
        List<CartDTO.CartItemDTO> itemDTOs = items.stream().map(item -> {
            FoodModel food = mongoTemplate.findById(item.getFoodId(), FoodModel.class);
    
            CartDTO.CartItemDTO dto = new CartDTO.CartItemDTO();
            dto.setFoodId(item.getFoodId());
            dto.setQuantity(item.getQuantity());
            dto.setFoodPrice(item.getPrice());
            dto.setTotalPrice(item.getPrice() * item.getQuantity());
    
            if (food != null) {
                dto.setFoodName(food.getName());
                dto.setFoodPicture(food.getImages() != null && !food.getImages().isEmpty()
                    ? food.getImages().get(0) : null);
            }
    
            return dto;
        }).collect(Collectors.toList());
    
        // Step 5: calculate total amount
        Integer totalAmount = itemDTOs.stream()
            .mapToInt(CartDTO.CartItemDTO::getTotalPrice)
            .sum();
    
        // Step 6: build user DTO
        CartDTO.CartUserDTO userDTO = new CartDTO.CartUserDTO();
        if (user != null) {
            userDTO.setId(user.getId());
            userDTO.setName(user.getFullName());
            userDTO.setEmail(user.getEmail());
        }
    
        // Step 7: build final DTO
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getId());
        cartDTO.setUser(userDTO);
        cartDTO.setItems(itemDTOs);
        cartDTO.setTotalAmount(totalAmount);
    
        return cartDTO;
    }
}

