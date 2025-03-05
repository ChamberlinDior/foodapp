package com.fooddelivery.service;

import com.fooddelivery.model.Livreur;
import com.fooddelivery.model.Order;
import com.fooddelivery.repository.LivreurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private LivreurRepository livreurRepository;

    // URL de l’API FCM
    private final String FCM_API_URL = "https://fcm.googleapis.com/fcm/send";
    // Remplacez cette valeur par votre clé serveur FCM
    private final String SERVER_KEY = "AAAA...votre-cle-FCM...";

    // Envoi de notification lors de l'assignation d'une commande au livreur
    public void sendLivreurAssignedNotification(Order order) {
        Optional<Livreur> livreurOpt = livreurRepository.findById(order.getLivreurId());
        if (livreurOpt.isPresent()) {
            Livreur livreur = livreurOpt.get();
            if (livreur.getDeviceToken() == null || livreur.getDeviceToken().isEmpty()) {
                System.out.println("Token non défini pour le livreur #" + livreur.getId());
                return;
            }
            String title = "Nouvelle commande assignée";
            String message = String.format("La commande #%d vous a été assignée.", order.getId());
            sendPushNotification(livreur.getDeviceToken(), title, message);
        } else {
            System.out.println("Livreur non trouvé pour la commande #" + order.getId());
        }
    }

    private void sendPushNotification(String deviceToken, String title, String message) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "key=" + SERVER_KEY);

        Map<String, Object> notification = new HashMap<>();
        notification.put("title", title);
        notification.put("body", message);

        Map<String, Object> payload = new HashMap<>();
        payload.put("to", deviceToken);
        payload.put("notification", notification);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(FCM_API_URL, request, String.class);
            System.out.println("Notification envoyée: " + response.getBody());
        } catch (Exception ex) {
            System.err.println("Erreur lors de l’envoi de la notification : " + ex.getMessage());
        }
    }
}
