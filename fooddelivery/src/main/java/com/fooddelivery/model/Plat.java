// src/main/java/com/fooddelivery/model/Plat.java
package com.fooddelivery.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "plats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;

    // Chargement EAGER pour sérialiser directement les images
    @OneToMany(mappedBy = "plat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PlatImage> images = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PlatType type = PlatType.MAIN;

    // Pour éviter la récursion, on ignore la propriété "plats" du restaurant
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @JsonIgnoreProperties({"plats", "hibernateLazyInitializer", "handler"})
    private Restaurant restaurant;
}
