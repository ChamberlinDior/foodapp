package com.fooddelivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "livreurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livreur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String numeroTelephone;

    @Lob
    @Column(name = "photo_profil", columnDefinition = "MEDIUMTEXT")
    private String photoProfil;

    private double latitude;
    private double longitude;
    private String motDePasse;

    // Nouveau champ pour le token de notification push
    private String deviceToken;
}
