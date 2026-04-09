package com.example.demo.services;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CartRepository;
import com.example.demo.model.CartModel;


@Service
public class CartService {
    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }

    public CartModel createAdd(CartModel data){
        CartModel cart = cartRepository.save(data);
        return cart;
    }
}
