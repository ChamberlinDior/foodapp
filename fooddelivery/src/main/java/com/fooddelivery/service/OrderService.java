package com.fooddelivery.service;

import com.fooddelivery.dto.OrderDTO;
import com.fooddelivery.model.Order;
import com.fooddelivery.repository.OrderRepository;
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
    private NotificationService notificationService;

    // Méthode pour sauvegarder une nouvelle commande
    public Order saveOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setClientName(orderDTO.getClientName());
        order.setClientEmail(orderDTO.getClientEmail());
        order.setRestaurantName(orderDTO.getRestaurantName());
        order.setItems(orderDTO.getItems());
        order.setQuantity(orderDTO.getQuantity());
        order.setPrice(orderDTO.getPrice());
        order.setOrderTime(LocalDateTime.now());
        order.setLatitude(orderDTO.getLatitude());
        order.setLongitude(orderDTO.getLongitude());
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

    // Méthode pour assigner un livreur à une commande et envoyer une notification
    public Optional<Order> assignLivreur(Long orderId, Long livreurId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setLivreurId(livreurId);
            orderRepository.save(order);

            // Envoi de la notification après l'assignation du livreur
            notificationService.sendLivreurAssignedNotification(order);
        }
        return orderOptional;
    }

    // Méthode pour assigner un livreur avec sa localisation
    public Optional<Order> assignLivreurWithLocation(Long orderId, Long livreurId, double livreurLatitude, double livreurLongitude) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setLivreurId(livreurId);
            order.setLivreurLocationLatitude(livreurLatitude);
            order.setLivreurLocationLongitude(livreurLongitude);
            orderRepository.save(order);

            // Envoi de la notification après l'assignation du livreur
            notificationService.sendLivreurAssignedNotification(order);
        }
        return orderOptional;
    }

    // Méthode pour supprimer une commande
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
