package com.example.demo.services;

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
    
}
