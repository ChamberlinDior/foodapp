package com.fooddelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private String clientName;
    private String clientEmail;
    private String restaurantName;
    private List<String> items;
    private int quantity;
    private double price;
    private LocalDateTime orderTime;
    private double latitude;
    private double longitude;

    // ID du livreur associé à la commande
    private Long livreurId;

    // Localisation spécifique du livreur
    private double livreurLocationLatitude;
    private double livreurLocationLongitude;
}
