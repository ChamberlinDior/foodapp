package com.fooddelivery.service;

import com.fooddelivery.model.Livreur;
import com.fooddelivery.model.Order;
import com.fooddelivery.repository.LivreurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private LivreurRepository livreurRepository;

    // Méthode pour envoyer une notification lorsque le livreur est assigné à une commande
    public void sendLivreurAssignedNotification(Order order) {
        Optional<Livreur> livreurOpt = livreurRepository.findById(order.getLivreurId());
        if (livreurOpt.isPresent()) {
            Livreur livreur = livreurOpt.get();
            String title = "Commande assignée à un livreur";
            String message = String.format(
                    "Commande #%d a été assignée au livreur:\n" +
                            "Nom: %s %s\n" +
                            "Téléphone: %s\n" +
                            "Photo de profil: %s\n" +
                            "Localisation actuelle: (Latitude: %f, Longitude: %f)",
                    order.getId(),
                    livreur.getNom(), livreur.getPrenom(),
                    livreur.getNumeroTelephone(),
                    livreur.getPhotoProfil(),
                    livreur.getLatitude(), livreur.getLongitude()
            );

            sendNotification(title, message, order.getId().intValue());
        } else {
            System.out.println("Livreur non trouvé pour la commande #" + order.getId());
        }
    }

    // Méthode générique pour envoyer une notification
    public void sendNotification(String title, String message, int orderId) {
        // Logique pour l’envoi de la notification (à adapter selon votre système de notification)
        System.out.println("Notification envoyée : " + title + " - " + message);
    }
}
