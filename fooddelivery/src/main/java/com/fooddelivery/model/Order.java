package com.fooddelivery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;
    private String clientEmail;
    private String restaurantName;
    private double price;
    private int quantity;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "item")
    private List<String> items;

    private LocalDateTime orderTime;
    private double latitude; // Localisation de la commande
    private double longitude; // Localisation de la commande

    // Localisation spécifique du livreur lors de l'association à la commande
    private double livreurLocationLatitude;
    private double livreurLocationLongitude;

    private Long livreurId;
}
