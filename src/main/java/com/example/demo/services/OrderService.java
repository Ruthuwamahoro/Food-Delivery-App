package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CartItemsRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.interfaces.*;
import com.example.demo.model.CartItemsModel;
import com.example.demo.model.CartModel;
import com.example.demo.model.FoodModel;
import com.example.demo.model.OrderModel;

import org.springframework.data.mongodb.core.MongoTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public OrderModel createOrder(String userId) {

        // Step 1: get user's cart
        CartModel cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart == null) {
            throw new RuntimeException("Cart not found for user");
        }

        // Step 2: get cart items
        List<CartItemsModel> cartItems = cartItemsRepository.findByCartId(cart.getId());
        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Step 3: snapshot items into order items
        List<OrderModel.ItemsOrder> orderItems = cartItems.stream().map(item -> {
            FoodModel food = mongoTemplate.findById(item.getFoodId(), FoodModel.class);

            OrderModel.ItemsOrder orderItem = new OrderModel.ItemsOrder();
            orderItem.setId(item.getFoodId());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getPrice() * item.getQuantity());

            if (food != null) {
                orderItem.setName(food.getName());
                orderItem.setImages(food.getImages() != null && !food.getImages().isEmpty()
                    ? food.getImages().get(0) : null);
            }

            return orderItem;
        }).collect(Collectors.toList());

        // Step 4: calculate total amount
        Integer totalAmount = orderItems.stream()
            .mapToInt(OrderModel.ItemsOrder::getTotalPrice)
            .sum();

        // Step 5: build order
        OrderModel order = new OrderModel();
        order.generateId();
        order.setUserId(userId);
        order.setCartId(cart.getId());
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt();
        order.setUpdatedAt();

        // Step 6: save order
        OrderModel savedOrder = orderRepository.save(order);

        // Step 7: clear cart items after order is created
        cartItemsRepository.deleteAll(cartItems);

        return savedOrder;
    }

    public List<OrderModel> getMyOrder(String id){
        return orderRepository.findByUserId(id);
    }
}