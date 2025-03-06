// OrderService.java
package com.fooddelivery.service;

import com.fooddelivery.dto.OrderDTO;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.model.User;
import com.fooddelivery.model.Livreur;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;
import com.fooddelivery.repository.LivreurRepository; // Ajouté
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

    @Autowired
    private LivreurRepository livreurRepository; // Ajouté

    // Création d'une nouvelle commande
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

        order.setRestaurantName(orderDTO.getRestaurantName());
        order.setItems(orderDTO.getItems());
        order.setQuantity(orderDTO.getQuantity());
        order.setPrice(orderDTO.getPrice());
        order.setOrderTime(LocalDateTime.now());

        order.setLatitude(orderDTO.getLatitude());
        order.setLongitude(orderDTO.getLongitude());

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

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByClientEmail(String clientEmail) {
        return orderRepository.findByClientEmail(clientEmail);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Méthode d'assignation du livreur (sans géolocalisation)
    public Optional<Order> assignLivreur(Long orderId, Long livreurId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setLivreurId(livreurId);
            // Récupérer les infos du livreur
            Optional<Livreur> livreurOpt = livreurRepository.findById(livreurId);
            if (livreurOpt.isPresent()) {
                Livreur livreur = livreurOpt.get();
                order.setLivreurNom(livreur.getNom());
                order.setLivreurPrenom(livreur.getPrenom());
                order.setLivreurNumeroTelephone(livreur.getNumeroTelephone());
                order.setLivreurPhotoProfil(livreur.getPhotoProfil());
            }
            orderRepository.save(order);
            notificationService.sendLivreurAssignedNotification(order);
        }
        return orderOptional;
    }

    // Méthode d'assignation du livreur avec mise à jour de la géolocalisation
    public Optional<Order> assignLivreurWithLocation(Long orderId, Long livreurId, double livreurLatitude, double livreurLongitude) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setLivreurId(livreurId);
            order.setLivreurLocationLatitude(livreurLatitude);
            order.setLivreurLocationLongitude(livreurLongitude);
            // Récupérer les infos du livreur
            Optional<Livreur> livreurOpt = livreurRepository.findById(livreurId);
            if (livreurOpt.isPresent()) {
                Livreur livreur = livreurOpt.get();
                order.setLivreurNom(livreur.getNom());
                order.setLivreurPrenom(livreur.getPrenom());
                order.setLivreurNumeroTelephone(livreur.getNumeroTelephone());
                order.setLivreurPhotoProfil(livreur.getPhotoProfil());
            }
            orderRepository.save(order);
            notificationService.sendLivreurAssignedNotification(order);
        }
        return orderOptional;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getUnconfirmedOrders() {
        return orderRepository.findByConfirmedFalse();
    }

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

    public Optional<Restaurant> getRestaurantByName(String name) {
        return restaurantRepository.findByName(name);
    }
}
