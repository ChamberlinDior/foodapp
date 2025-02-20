// src/main/java/com/fooddelivery/repository/PlatRepository.java
package com.fooddelivery.repository;

import com.fooddelivery.model.Plat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlatRepository extends JpaRepository<Plat, Long> {

    @Query("SELECT DISTINCT p FROM Plat p LEFT JOIN FETCH p.images WHERE p.restaurant.id = :restaurantId")
    List<Plat> findByRestaurantId(@Param("restaurantId") Long restaurantId);
}
