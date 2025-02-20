package com.fooddelivery.service;

import com.fooddelivery.dto.OrderDTO;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.model.User;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.RestaurantRepository;
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
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private NotificationService notificationService;

    // Création d'une nouvelle commande avec géolocalisation du client et du restaurant.
    // Les coordonnées du client sont fournies par le front, celles du restaurant sont récupérées à partir du nom.
    public Order saveOrder(OrderDTO orderDTO) {
        Order order = new Order();

        // Récupérer l'utilisateur à partir de l'email
        Optional<User> userOptional = userRepository.findByEmail(orderDTO.getClientEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            order.setClientName(user.getFirstName() + " " + user.getLastName());
            order.setClientEmail(user.getEmail());
            order.setClientPhoneNumber(user.getPhoneNumber());
        } else {
            throw new IllegalArgumentException("Utilisateur introuvable avec l'email : " + orderDTO.getClientEmail());
        }

        // Enregistrer les informations de la commande
        order.setRestaurantName(orderDTO.getRestaurantName());
        order.setItems(orderDTO.getItems());
        order.setQuantity(orderDTO.getQuantity());
        order.setPrice(orderDTO.getPrice());
        order.setOrderTime(LocalDateTime.now());

        // Géolocalisation du client (fournie par le front)
        order.setLatitude(orderDTO.getLatitude());
        order.setLongitude(orderDTO.getLongitude());

        // Récupérer le restaurant et affecter sa géolocalisation
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByName(orderDTO.getRestaurantName());
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            order.setRestaurantLatitude(restaurant.getLatitude());
            order.setRestaurantLongitude(restaurant.getLongitude());
        } else {
            throw new IllegalArgumentException("Restaurant introuvable avec le nom : " + orderDTO.getRestaurantName());
        }

        return orderRepository.save(order);
    }

    // Sauvegarder une commande déjà construite
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Récupérer toutes les commandes
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Récupérer les commandes d'un client à partir de son email
    public List<Order> getOrdersByClientEmail(String clientEmail) {
        return orderRepository.findByClientEmail(clientEmail);
    }

    // Récupérer une commande par son ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Assigner un livreur à une commande
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

    // Assigner un livreur à une commande en ajoutant sa géolocalisation
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

    // Supprimer une commande par son ID
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

    // Méthode pour récupérer un restaurant par son nom
    public Optional<Restaurant> getRestaurantByName(String name) {
        return restaurantRepository.findByName(name);
    }
}
