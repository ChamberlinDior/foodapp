// OrderDTO.java
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
    private String clientPhoneNumber; // Nouveau champ pour le numéro de téléphone
    private String restaurantName;
    private List<String> items;
    private int quantity;
    private double price;
    private LocalDateTime orderTime;
    private double latitude;   // Géolocalisation du client (latitude)
    private double longitude;  // Géolocalisation du client (longitude)

    // Pour l’assignation du livreur
    private Long livreurId;
    private double livreurLocationLatitude;
    private double livreurLocationLongitude;

    // Nouveaux champs pour les informations du livreur
    private String livreurNom;
    private String livreurPrenom;
    private String livreurNumeroTelephone;
    private String livreurPhotoProfil;

    private boolean confirmed; // Champ optionnel
}
