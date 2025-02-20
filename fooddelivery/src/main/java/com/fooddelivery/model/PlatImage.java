// src/main/java/com/fooddelivery/model/PlatImage.java
package com.fooddelivery.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Base64;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "plat_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] data;

    private String contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plat_id")
    private Plat plat;

    // Getter personnalisé pour retourner les données en Base64 dans le JSON
    @JsonProperty("data")
    public String getDataBase64() {
        return data != null ? Base64.getEncoder().encodeToString(data) : null;
    }
}
