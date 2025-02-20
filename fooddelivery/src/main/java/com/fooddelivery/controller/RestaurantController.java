package com.fooddelivery.controller;

import com.fooddelivery.dto.RestaurantDTO;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        Restaurant savedRestaurant = restaurantService.saveRestaurant(restaurantDTO);
        return ResponseEntity.ok(savedRestaurant);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);
        return restaurant.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint pour uploader le logo du restaurant
    @PostMapping("/{id}/uploadLogo")
    public ResponseEntity<Restaurant> uploadLogo(@PathVariable Long id,
                                                 @RequestParam("file") MultipartFile file) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurantLogo(id, file);
        return ResponseEntity.ok(updatedRestaurant);
    }
}
