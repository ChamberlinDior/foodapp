package com.fooddelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private String name;
    private String date;
    private String time;
    private String salon;
    private String restaurantName;
    private String userEmail;
}
