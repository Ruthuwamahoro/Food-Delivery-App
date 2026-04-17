package com.example.demo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.CartItemsRepository;
import com.example.demo.model.CartItemsModel;
import com.example.demo.model.CartModel;
import com.example.demo.services.AuthService;
import com.example.demo.services.CartItemsService;
import com.example.demo.services.CartService;
import com.example.demo.utils.SendResponse;

@RestController
@RequestMapping(value="/api/carts/items")
public class CartItemController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CartItemsService cartItemsService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemsRepository cartItemsRepository;


    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SendResponse<CartItemsModel>> addItemsToCart(
        @RequestBody CartItemsModel body, 
        @RequestHeader(value = "Authorization", required = true) String authHeader
    ){
        System.out.println("===================");

        boolean isAuthenticated = authService.isAuthenticated(authHeader);
        if(!isAuthenticated){  
            return ResponseEntity.status(401)
                .body(new SendResponse<>("error", "Unauthorized", null));
        }
    
        try {
            if(body.getFoodId() == null || body.getQuantity() == null || body.getPrice() == null){
                return ResponseEntity.status(400)
                    .body(new SendResponse<>("error", "All fields are required", null));
            }
    
            String token = authService.getToken(authHeader);
            String userId = authService.getUserId(token);
    
            CartModel cart = cartService.getOrCreateCart(userId); 

            Optional<CartItemsModel> existingItem = cartItemsRepository
            .findByCartIdAndFoodId(cart.getId(), body.getFoodId());
            if(existingItem.isPresent()){
                return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SendResponse<>("error", "Items already exists", null));
            }
           
            
            CartItemsModel cartData = new CartItemsModel();
            cartData.generateId();
            cartData.setCartId(cart.getId()); 
            cartData.setFoodId(body.getFoodId());
            cartData.setQuantity(body.getQuantity());
            cartData.setPrice(body.getPrice());
            cartData.setCreatedAt();
            cartData.setUpdatedAt();
    
            cartItemsService.AddItemsToCart(cartData);
    
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SendResponse<>("success", "Item added to cart successfully", cartData));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new SendResponse<>("error", e.getMessage(), null));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SendResponse<CartItemsModel>> updateCartById(
        @RequestHeader(value = "Authorization", required = true) String authHeader,
        @PathVariable String id,
        @RequestBody Map<String, Object> body) {

        boolean isAuthenticated = authService.isAuthenticated(authHeader);
        if (!isAuthenticated) {
            return ResponseEntity.status(401)
                .body(new SendResponse<>("error", "Unauthorized", null));
        }

        Optional<CartItemsModel> findItemId = cartItemsRepository.findById(id);
        if (!findItemId.isPresent()) {
            return ResponseEntity.status(404)
                .body(new SendResponse<>("error", "id not found", null));
        }

        try {
            CartItemsModel item = findItemId.get();

            // ✅ Only update fields that are present in the request body
            if (body.containsKey("quantity")) {
                item.setQuantity((Integer) body.get("quantity"));
            }
            if (body.containsKey("price")) {
                item.setPrice((Integer) body.get("price"));
            }
            if (body.containsKey("foodId")) {
                item.setFoodId((String) body.get("foodId"));
            }
            // add more fields as needed...

            CartItemsModel updated = cartItemsRepository.save(item);

            return ResponseEntity.status(200)
                .body(new SendResponse<>("success", "item updated successfully", updated));

        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new SendResponse<>("error", e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SendResponse<CartItemsModel>> deleteCartById(@PathVariable String id){
        try {

            Optional<CartItemsModel> findItemId = cartItemsRepository.findById(id);
            if(findItemId == null){
                SendResponse<CartItemsModel> response =new SendResponse<CartItemsModel>("error", "id not found", null);
                return ResponseEntity.status(401).body(response);
            }
    
            boolean info = cartItemsService.deleteItem(id);
            if(!info){
                SendResponse<CartItemsModel> response =new SendResponse<CartItemsModel>("error", "item not found", null);
                return ResponseEntity.status(400).body(response); 
            }
            SendResponse<CartItemsModel> response =new SendResponse<CartItemsModel>("success", "item deleted successfully", null);
            return ResponseEntity.status(200).body(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new SendResponse<>("error", e.getMessage(), null));
        }


    }

    


}
