package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.Optional;

import com.cloudinary.Cloudinary;

// import org.springframework.data.domain.Page;
// import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.FoodModel;
import com.example.demo.services.CloudinaryService;
import com.example.demo.services.FoodService;
import com.example.demo.utils.SendResponse;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private CloudinaryService cloudinaryService;
    
    @GetMapping
    public ResponseEntity<SendResponse<List<FoodModel>>> getAllFoods() {
        try {
            List<FoodModel> AllFoodsInfo = foodService.getAllFoods();
            if(AllFoodsInfo == null ||AllFoodsInfo.isEmpty()){
                SendResponse<List<FoodModel>> responseData = new SendResponse<>("success", "No food items found", null);
    
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
            }
    
            SendResponse<List<FoodModel>> response = new SendResponse<>("success", "List of all food items", AllFoodsInfo);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            SendResponse<List<FoodModel>> response = new SendResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<SendResponse<FoodModel>> getSingleFood(@PathVariable String id){
        try{
            Optional<FoodModel> AllFoodsInfo = foodService.getFoodById(id);
            if(AllFoodsInfo == null ||AllFoodsInfo.isEmpty()){
                SendResponse<FoodModel> responseData = new SendResponse<>("success", "No food item found", null);
    
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
            }
            SendResponse<FoodModel> response = new SendResponse<>("success", "Food Item returned successfully", AllFoodsInfo.get());
            return ResponseEntity.ok(response);
        } catch(Exception e){
            SendResponse<FoodModel> response = new SendResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PostMapping(
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SendResponse<FoodModel>> addFood(
        @RequestParam("name") String name,
        @RequestParam("description") String description,
        @RequestParam("price") Integer price,
        @RequestParam("deliveryTime") String deliveryTime,
        @RequestParam("images") List<MultipartFile> images
    ){
        try {

            FoodModel food = new FoodModel();
            food.generateId();
            food.setName(name);
            food.setDescription(description);
            food.setPrice(price);
            food.setDeliveryTime(deliveryTime);
            if (images != null && !images.isEmpty()) {
                List<String> uploadedUrls = cloudinaryService.uploadMultipleFiles(images, "foods");
                food.setImages(uploadedUrls);
            }
    
            FoodModel savedFood = foodService.addFood(food);
            if (savedFood == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new SendResponse<>("error", "Failed to add food item", null));
            }
    
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SendResponse<>("success", "Food item added successfully", savedFood));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new SendResponse<>("error", "Image upload failed: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SendResponse<FoodModel>> deleteFood(@PathVariable String id){
        try{
            Optional<FoodModel> findFood = foodService.getFoodById(id);
            if(findFood.isEmpty()){
                SendResponse<FoodModel> responseData = new SendResponse("error", "Food item not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
            }
            foodService.deleteFood(id);
            SendResponse<FoodModel> response = new SendResponse<>("success", "Food item deleted successfully", null);
            return ResponseEntity.ok(response);

        } catch(Exception e){
            SendResponse<FoodModel> response = new SendResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SendResponse<FoodModel>> updateFood(@PathVariable String id, @RequestBody FoodModel food){
        try{
            FoodModel updatedFood = foodService.updateFood(id, food);
            if(updatedFood == null){
                SendResponse<FoodModel> responseData = new SendResponse<>("error", "Food item not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
            }
            SendResponse<FoodModel> response = new SendResponse<>("success", "Food item updated successfully", null);
            return ResponseEntity.ok(response);

        } catch(Exception e){
            SendResponse<FoodModel> response = new SendResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
}