package com.fooddelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivreurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String numeroTelephone;
    private String photoProfil; // URL ou chemin vers la photo de profil
    private double latitude;
    private double longitude;
    private String motDePasse;
    // Nouveau champ pour le token de notification push
    private String deviceToken;
}
