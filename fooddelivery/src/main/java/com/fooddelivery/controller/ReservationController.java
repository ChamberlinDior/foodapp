package com.fooddelivery.controller;

import com.fooddelivery.dto.ReservationDTO;
import com.fooddelivery.model.Reservation;
import com.fooddelivery.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservationDTO) {
        Reservation savedReservation = reservationService.saveReservation(reservationDTO);
        return ResponseEntity.ok(savedReservation);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Reservation>> getReservationsByUserEmail(@PathVariable String email) {
        List<Reservation> reservations = reservationService.getReservationsByUserEmail(email);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok("Réservation supprimée avec succès.");
    }

    // Point de terminaison pour obtenir les réservations non lues
    @GetMapping("/unread")
    public ResponseEntity<List<Reservation>> getUnreadReservations() {
        List<Reservation> unreadReservations = reservationService.getUnreadReservations();
        return ResponseEntity.ok(unreadReservations);
    }
}
