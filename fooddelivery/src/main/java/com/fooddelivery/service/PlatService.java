// src/main/java/com/fooddelivery/service/PlatService.java
package com.fooddelivery.service;

import com.fooddelivery.dto.PlatDTO;
import com.fooddelivery.model.Plat;
import com.fooddelivery.model.PlatImage;
import com.fooddelivery.model.PlatType;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.repository.PlatRepository;
import com.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class PlatService {

    @Autowired
    private PlatRepository platRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Plat savePlat(PlatDTO platDTO) {
        Plat plat = new Plat();
        plat.setName(platDTO.getName());
        plat.setDescription(platDTO.getDescription());
        plat.setPrice(platDTO.getPrice());
        // Si le type n'est pas précisé, on utilise MAIN par défaut.
        plat.setType(platDTO.getType() != null ? platDTO.getType() : PlatType.MAIN);

        // Association avec le restaurant
        Restaurant restaurant = restaurantRepository.findById(platDTO.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant introuvable"));
        plat.setRestaurant(restaurant);
        return platRepository.save(plat);
    }

    public List<Plat> getPlatsByRestaurant(Long restaurantId) {
        return platRepository.findByRestaurantId(restaurantId);
    }

    // Upload d'une image pour un plat
    public Plat addImageToPlat(Long platId, MultipartFile imageFile) {
        Plat plat = platRepository.findById(platId)
                .orElseThrow(() -> new IllegalArgumentException("Plat non trouvé"));
        try {
            if (imageFile.isEmpty()) {
                throw new RuntimeException("Fichier vide !");
            }
            byte[] fileData = imageFile.getBytes();
            System.out.println("Taille du fichier reçu : " + fileData.length + " octets");

            PlatImage platImage = new PlatImage();
            platImage.setData(fileData);
            platImage.setContentType(imageFile.getContentType());
            platImage.setPlat(plat);
            plat.getImages().add(platImage);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier image", e);
        }
        return platRepository.save(plat);
    }
}
