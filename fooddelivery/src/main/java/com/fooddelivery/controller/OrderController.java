package com.fooddelivery.controller;

import com.fooddelivery.dto.AssignLivreurDTO;
import com.fooddelivery.dto.OrderDTO;
import com.fooddelivery.model.Order;
import com.fooddelivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Endpoint pour créer une commande
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        // Sauvegarde de la commande avec récupération du numéro de téléphone et du nom du client
        Order savedOrder = orderService.saveOrder(orderDTO);
        return ResponseEntity.ok(savedOrder);
    }

    // Endpoint pour obtenir toutes les commandes
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Endpoint pour obtenir les commandes d'un client par email
    @GetMapping("/user/{email}")
    public ResponseEntity<List<Order>> getOrdersByClientEmail(@PathVariable String email) {
        List<Order> orders = orderService.getOrdersByClientEmail(email);
        return ResponseEntity.ok(orders);
    }

    // Endpoint pour obtenir une commande par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint pour assigner un livreur à une commande
    @PutMapping("/assignLivreur")
    public ResponseEntity<Order> assignLivreurToOrder(@RequestBody AssignLivreurDTO assignLivreurDTO) {
        Optional<Order> updatedOrder = orderService.assignLivreur(assignLivreurDTO.getOrderId(), assignLivreurDTO.getLivreurId());
        return updatedOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint pour marquer la commande comme livrée avec localisation du livreur
    @PutMapping("/deliver")
    public ResponseEntity<Order> markOrderAsDelivered(@RequestBody AssignLivreurDTO assignLivreurDTO) {
        Optional<Order> updatedOrder = orderService.assignLivreurWithLocation(
                assignLivreurDTO.getOrderId(),
                assignLivreurDTO.getLivreurId(),
                assignLivreurDTO.getLivreurLocationLatitude(),
                assignLivreurDTO.getLivreurLocationLongitude()
        );
        return updatedOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint pour supprimer une commande par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Commande supprimée avec succès.");
    }

    // Endpoint pour confirmer une commande
    @PutMapping("/{id}/confirm")
    public ResponseEntity<Order> confirmOrder(@PathVariable Long id) {
        Optional<Order> orderOptional = orderService.confirmOrder(id);
        return orderOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint pour récupérer les commandes non confirmées
    @GetMapping("/unconfirmed")
    public ResponseEntity<List<Order>> getUnconfirmedOrders() {
        List<Order> orders = orderService.getUnconfirmedOrders();
        return ResponseEntity.ok(orders);
    }
}
