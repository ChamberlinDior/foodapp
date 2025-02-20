package com.fooddelivery.controller;

import com.fooddelivery.dto.AssignLivreurDTO;
import com.fooddelivery.dto.OrderDTO;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.Restaurant;
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

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        Order savedOrder = orderService.saveOrder(orderDTO);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Order>> getOrdersByClientEmail(@PathVariable String email) {
        List<Order> orders = orderService.getOrdersByClientEmail(email);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/assignLivreur")
    public ResponseEntity<Order> assignLivreurToOrder(@RequestBody AssignLivreurDTO assignLivreurDTO) {
        Optional<Order> updatedOrder = orderService.assignLivreur(assignLivreurDTO.getOrderId(), assignLivreurDTO.getLivreurId());
        return updatedOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Commande supprimée avec succès.");
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<Order> confirmOrder(@PathVariable Long id) {
        Optional<Order> orderOptional = orderService.confirmOrder(id);
        return orderOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/unconfirmed")
    public ResponseEntity<List<Order>> getUnconfirmedOrders() {
        List<Order> orders = orderService.getUnconfirmedOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/restaurant/{restaurantName}")
    public ResponseEntity<Restaurant> getRestaurantByName(@PathVariable String restaurantName) {
        Optional<Restaurant> restaurant = orderService.getRestaurantByName(restaurantName);
        return restaurant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
