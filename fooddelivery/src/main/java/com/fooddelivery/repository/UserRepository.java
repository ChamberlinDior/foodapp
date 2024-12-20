package com.fooddelivery.repository;

import com.fooddelivery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Rechercher un utilisateur par email
    Optional<User> findByEmail(String email);

    // Vérifier si un email existe déjà
    boolean existsByEmail(String email);

    // Rechercher un utilisateur par email et mot de passe (non recommandé sans hashage)
    Optional<User> findByEmailAndPassword(String email, String password);
}
