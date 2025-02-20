package com.fooddelivery.service;

import com.fooddelivery.dto.UserDTO;
import com.fooddelivery.model.User;
import com.fooddelivery.model.UserRole;
import com.fooddelivery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Enregistrement d'un utilisateur avec validation
    @Transactional
    public User saveUser(UserDTO userDTO) {
        validateUserDTO(userDTO);

        // Vérification de l'existence de l'email
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole(userDTO.getRole() != null ? userDTO.getRole() : UserRole.CLIENT);
        user.setPassword(userDTO.getPassword()); // Pensez à ajouter du hashage ici pour sécuriser les mots de passe

        // Affectation des coordonnées de géolocalisation (peuvent être null)
        user.setLatitude(userDTO.getLatitude());
        user.setLongitude(userDTO.getLongitude());

        return userRepository.save(user);
    }

    // Récupération de tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Récupération d'un utilisateur par ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Récupération d'un utilisateur par email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Suppression d'un utilisateur
    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }

    // Connexion utilisateur
    public Optional<User> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    // Validation des champs obligatoires
    private void validateUserDTO(UserDTO userDTO) {
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (userDTO.getFirstName() == null || userDTO.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (userDTO.getLastName() == null || userDTO.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
    }
}
