// src/main/java/com/fooddelivery/controller/PlatController.java
package com.fooddelivery.controller;

import com.fooddelivery.dto.PlatDTO;
import com.fooddelivery.model.Plat;
import com.fooddelivery.service.PlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/plats")
public class PlatController {

    @Autowired
    private PlatService platService;

    @PostMapping
    public ResponseEntity<Plat> createPlat(@RequestBody PlatDTO platDTO) {
        Plat savedPlat = platService.savePlat(platDTO);
        return ResponseEntity.ok(savedPlat);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Plat>> getPlatsByRestaurant(@PathVariable Long restaurantId) {
        List<Plat> plats = platService.getPlatsByRestaurant(restaurantId);
        return ResponseEntity.ok(plats);
    }

    // Endpoint pour uploader une image et l'ajouter à un plat
    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<Plat> uploadImage(@PathVariable Long id,
                                            @RequestParam("file") MultipartFile file) {
        Plat updatedPlat = platService.addImageToPlat(id, file);
        return ResponseEntity.ok(updatedPlat);
    }
}
