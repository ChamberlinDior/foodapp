package com.fooddelivery.repository;

import com.fooddelivery.model.Livreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivreurRepository extends JpaRepository<Livreur, Long> {
    Optional<Livreur> findByNomAndMotDePasse(String nom, String motDePasse);
}
