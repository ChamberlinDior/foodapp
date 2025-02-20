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

    // Utilisation de MEDIUMTEXT pour permettre de stocker des données très longues
    @Lob
    @Column(name = "photo_profil", columnDefinition = "MEDIUMTEXT")
    private String photoProfil; // URL ou chemin vers la photo de profil

    private double latitude;
    private double longitude;
    private String motDePasse;
}
