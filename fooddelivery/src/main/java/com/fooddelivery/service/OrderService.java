package com.fooddelivery.service;

import com.fooddelivery.dto.OrderDTO;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.User;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository; // Injection du UserRepository

    @Autowired
    private NotificationService notificationService;

    // Sauvegarder une nouvelle commande à partir d'un DTO
    public Order saveOrder(OrderDTO orderDTO) {
        Order order = new Order();

        // Récupérer l'utilisateur à partir de l'email
        Optional<User> userOptional = userRepository.findByEmail(orderDTO.getClientEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            order.setClientName(user.getFirstName() + " " + user.getLastName());
            order.setClientEmail(user.getEmail());
            order.setClientPhoneNumber(user.getPhoneNumber()); // Enregistrer le numéro de téléphone
        } else {
            throw new IllegalArgumentException("Utilisateur introuvable avec l'email : " + orderDTO.getClientEmail());
        }

        // Enregistrer les autres informations de la commande
        order.setRestaurantName(orderDTO.getRestaurantName());
        order.setItems(orderDTO.getItems());
        order.setQuantity(orderDTO.getQuantity());
        order.setPrice(orderDTO.getPrice());
        order.setOrderTime(LocalDateTime.now());
        order.setLatitude(orderDTO.getLatitude());
        order.setLongitude(orderDTO.getLongitude());
        // confirmed est déjà false par défaut

        return orderRepository.save(order);
    }

    // Méthode pour sauvegarder (ou mettre à jour) une commande existante
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Méthode pour obtenir toutes les commandes
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Méthode pour obtenir les commandes d'un client par email
    public List<Order> getOrdersByClientEmail(String clientEmail) {
        return orderRepository.findByClientEmail(clientEmail);
    }

    // Méthode pour obtenir une commande par son ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Assignation d’un livreur à une commande
    public Optional<Order> assignLivreur(Long orderId, Long livreurId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setLivreurId(livreurId);
            orderRepository.save(order);
            notificationService.sendLivreurAssignedNotification(order);
        }
        return orderOptional;
    }

    // Assignation d’un livreur avec localisation
    public Optional<Order> assignLivreurWithLocation(Long orderId, Long livreurId, double livreurLatitude, double livreurLongitude) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setLivreurId(livreurId);
            order.setLivreurLocationLatitude(livreurLatitude);
            order.setLivreurLocationLongitude(livreurLongitude);
            orderRepository.save(order);
            notificationService.sendLivreurAssignedNotification(order);
        }
        return orderOptional;
    }

    // Supprimer une commande
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Récupérer les commandes non confirmées
    public List<Order> getUnconfirmedOrders() {
        return orderRepository.findByConfirmedFalse();
    }

    // Confirmer une commande
    public Optional<Order> confirmOrder(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setConfirmed(true);
            order = orderRepository.save(order);
            return Optional.of(order);
        }
        return Optional.empty();
    }
}
