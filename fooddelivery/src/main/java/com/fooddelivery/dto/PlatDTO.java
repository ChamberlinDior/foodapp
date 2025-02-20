package com.fooddelivery.dto;

import com.fooddelivery.model.PlatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatDTO {
    private String name;
    private String description;
    private double price;
    // Identifiant du restaurant auquel le plat sera associé
    private Long restaurantId;
    // Type du plat (MAIN, COCKTAIL, ACCOMPAGNEMENT)
    private PlatType type;
}
