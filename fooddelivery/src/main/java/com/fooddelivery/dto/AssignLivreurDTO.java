package com.fooddelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignLivreurDTO {
    private Long orderId;
    private Long livreurId;

    // Localisation spécifique du livreur
    private double livreurLocationLatitude;
    private double livreurLocationLongitude;
}
