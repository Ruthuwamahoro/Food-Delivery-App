package com.example.demo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.Repository.CartItemsRepository;
import com.example.demo.model.CartItemsModel;
import com.example.demo.model.CartModel;

@Service
public class CartItemsService {

    private CartItemsRepository cartItemsRepository;

    public CartItemsService(CartItemsRepository cartItemsRepository){
        this.cartItemsRepository = cartItemsRepository;
    }

    public CartItemsModel AddItemsToCart(CartItemsModel data){
        CartItemsModel addToCART = cartItemsRepository.save(data);
        return addToCART;
    }
    
}
