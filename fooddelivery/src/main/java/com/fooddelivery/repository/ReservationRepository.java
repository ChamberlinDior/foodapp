package com.fooddelivery.repository;

import com.fooddelivery.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserEmail(String userEmail);
    List<Reservation> findByUnreadTrue(); // Méthode pour les réservations non lues
}
