package com.fooddelivery.service;

import com.fooddelivery.dto.RestaurantDTO;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Sauvegarde d'un restaurant avec ses informations de base.
     *
     * @param restaurantDTO DTO contenant les informations du restaurant.
     * @return Restaurant sauvegardé.
     */
    public Restaurant saveRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDTO.getName());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setLatitude(restaurantDTO.getLatitude());
        restaurant.setLongitude(restaurantDTO.getLongitude());

        return restaurantRepository.save(restaurant);
    }

    /**
     * Récupérer tous les restaurants enregistrés.
     *
     * @return Liste des restaurants.
     */
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    /**
     * Récupérer un restaurant par son ID.
     *
     * @param id Identifiant du restaurant.
     * @return Restaurant correspondant, s'il existe.
     */
    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    /**
     * Récupérer un restaurant par son nom.
     *
     * @param name Nom du restaurant.
     * @return Restaurant correspondant, s'il existe.
     */
    public Optional<Restaurant> getRestaurantByName(String name) {
        return restaurantRepository.findByName(name);
    }

    /**
     * Mise à jour du logo du restaurant.
     *
     * @param restaurantId ID du restaurant.
     * @param logoFile     Fichier image du logo.
     * @return Restaurant mis à jour avec le logo.
     */
    public Restaurant updateRestaurantLogo(Long restaurantId, MultipartFile logoFile) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant non trouvé avec l'ID : " + restaurantId));

        String fileName = fileStorageService.storeFile(logoFile);
        String fileDownloadUri = "/files/" + fileName;
        restaurant.setLogoUrl(fileDownloadUri);

        return restaurantRepository.save(restaurant);
    }
}
