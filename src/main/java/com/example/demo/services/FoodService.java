package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.FoodRepository;
import com.example.demo.model.FoodModel;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository){
        this.foodRepository = foodRepository;
    }

    public List<FoodModel> getAllFoods(){
        if(foodRepository.findAll().isEmpty()){
            return null;
        }
        return foodRepository.findAll();
    }

    public Optional<FoodModel> getFoodById(String id){
        Optional<FoodModel> food = foodRepository.findById(id);
        return food;
    }

    public FoodModel addFood(FoodModel food){
        FoodModel savedFood = foodRepository.save(food);
        return savedFood;
    }

    public boolean deleteFood(String id){
        if(!foodRepository.findById(id).isPresent()){
            return false;
        }

        foodRepository.deleteById(id);
        return true;
    }

    public FoodModel updateFood(String id, FoodModel food){
        Optional<FoodModel> findFoodById = foodRepository.findById(id);
        if(findFoodById.isPresent()){
            FoodModel existingFood = findFoodById.get();
            if(food.getName() != null){
                existingFood.setName(food.getName());
            }
            if(food.getDescription() != null){
                existingFood.setDescription(food.getDescription());
            }
            if(food.getPrice() != null){
                existingFood.setPrice(food.getPrice());
            }
            FoodModel updatedFood = foodRepository.save(existingFood);
            return updatedFood;
        }
        return null;
    }

    // // Get Food by pagination
    // public Page<FoodModel> getFoodByPagination(int page, int size, String sortBy){
    //     Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
    //     Page<FoodModel> foodPage = foodRepository.findAll(pageable);
    //     return foodPage;
    // }

    // // Search Food by name 
    // public FoodModel searchFoodByName(String name){
    //     List<FoodModel> foods = foodRepository.findByNameContainingIgnoreCase(name);
    //     if(foods.isEmpty()){
    //         return null;
    //     }
    //     return foods.get(0);
    // }

    // // search Food by price
    // public List<FoodModel> searchFoodByPrice(String price){
    //     List<FoodModel> foods = foodRepository.findByPrice(price);
    //     if(foods.isEmpty()){
    //         return null;
    //     }
    //     return foods;
    // }

    // // search Food by description
    // public List<FoodModel> searchFoodByDescription(String description){
    //     List<FoodModel> foods = foodRepository.findByDescriptionContaining(description);
    //     if(foods.isEmpty()){
    //         return null;
    //     }
    //     return foods;
    // }

    // // search Food by name with pagination
    // public Page<FoodModel> searchFoodByNameWithPagination(String name, int page, int size, String sortBy){
    //     Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
    //     Page<FoodModel> foodPage = foodRepository.findByNameContaining(name, pageable);
    //     return foodPage;
    // }
    
}
