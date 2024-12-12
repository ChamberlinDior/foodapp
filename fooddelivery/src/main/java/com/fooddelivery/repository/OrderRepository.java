package com.fooddelivery.repository;

import com.fooddelivery.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientEmail(String clientEmail);
    List<Order> findByLivreurId(Long livreurId); // Méthode pour récupérer les commandes par livreur
}
