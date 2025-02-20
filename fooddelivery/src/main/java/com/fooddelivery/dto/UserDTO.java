package com.fooddelivery.dto;

import com.fooddelivery.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private String password;

    // Champs de géolocalisation pour le client
    private Double latitude;
    private Double longitude;
}
