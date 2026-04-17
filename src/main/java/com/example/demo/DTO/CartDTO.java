package com.example.demo.DTO;
import java.util.List;

import lombok.Data;

@Data
public class CartDTO {
    private String cartId;
    private CartUserDTO user;        
    private List<CartItemDTO> items;
    private Integer totalAmount;

    @Data
    public static class CartUserDTO {
        private String id;
        private String name;
        private String email;
    }

    @Data
    public static class CartItemDTO {
        private String cartItemId;
        private String foodId;
        private String foodName;
        private String foodPicture;
        private Integer foodPrice;
        private Integer quantity;
        private Integer totalPrice;
    }
}