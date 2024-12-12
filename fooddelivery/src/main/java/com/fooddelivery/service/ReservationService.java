package com.fooddelivery.service;

import com.fooddelivery.dto.ReservationDTO;
import com.fooddelivery.model.Reservation;
import com.fooddelivery.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation saveReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setName(reservationDTO.getName());
        reservation.setDate(reservationDTO.getDate());
        reservation.setTime(reservationDTO.getTime());
        reservation.setSalon(reservationDTO.getSalon());
        reservation.setRestaurantName(reservationDTO.getRestaurantName());
        reservation.setUserEmail(reservationDTO.getUserEmail());
        reservation.setUnread(true); // Marquer comme non lue lors de la création
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByUserEmail(String userEmail) {
        return reservationRepository.findByUserEmail(userEmail);
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    // Méthode pour récupérer les réservations non lues et les marquer comme lues
    public List<Reservation> getUnreadReservations() {
        List<Reservation> unreadReservations = reservationRepository.findByUnreadTrue();
        unreadReservations.forEach(reservation -> reservation.setUnread(false));
        reservationRepository.saveAll(unreadReservations);
        return unreadReservations;
    }
}
