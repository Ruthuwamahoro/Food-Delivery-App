package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CartItemsRepository;
import com.example.demo.model.CartItemsModel;

@Service
public class CartItemsService {

    @Autowired
    private CartItemsRepository cartItemsRepository;

    public CartItemsService(CartItemsRepository cartItemsRepository){
        this.cartItemsRepository = cartItemsRepository;
    }

    public CartItemsModel AddItemsToCart(CartItemsModel data){
        CartItemsModel addToCART = cartItemsRepository.save(data);
        return addToCART;
    }

    public CartItemsModel updateItem(String id, CartItemsModel data) {

        CartItemsModel existingItem = cartItemsRepository.findById(id).orElse(null);
        if (existingItem == null) {
            return null;
        }
    
        if (data.getFoodId() != null) {
            existingItem.setFoodId(data.getFoodId());
        }
        if (data.getQuantity() != null) {
            existingItem.setQuantity(data.getQuantity());
        }
    
        existingItem.setUpdatedAt();
    
        return cartItemsRepository.save(existingItem); // save existing, not new
    }

    public boolean deleteItem(String id){
      
        if(!cartItemsRepository.findById(id).isPresent()){
            return false;
        }
        System.out.println("++++" + id);
        cartItemsRepository.deleteById(id);
        return true;
        
    }
    
}
