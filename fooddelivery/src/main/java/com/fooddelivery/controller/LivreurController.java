package com.fooddelivery.controller;

import com.fooddelivery.dto.LivreurDTO;
import com.fooddelivery.model.Livreur;
import com.fooddelivery.service.LivreurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livreurs")
public class LivreurController {

    @Autowired
    private LivreurService livreurService;

    // Endpoint pour créer un livreur
    @PostMapping
    public ResponseEntity<Livreur> createLivreur(@RequestBody LivreurDTO livreurDTO) {
        Livreur savedLivreur = livreurService.saveLivreur(livreurDTO);
        return ResponseEntity.ok(savedLivreur);
    }

    // Endpoint pour authentifier un livreur
    @PostMapping("/login")
    public ResponseEntity<Livreur> loginLivreur(@RequestBody LivreurDTO loginRequest) {
        Optional<Livreur> livreur = livreurService.authenticate(loginRequest.getNom(), loginRequest.getMotDePasse());
        return livreur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());
    }

    // Endpoint pour obtenir la liste de tous les livreurs
    @GetMapping
    public ResponseEntity<List<Livreur>> getAllLivreurs() {
        List<Livreur> livreurs = livreurService.getAllLivreurs();
        return ResponseEntity.ok(livreurs);
    }

    // Endpoint pour obtenir les détails d'un livreur par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Livreur> getLivreurById(@PathVariable Long id) {
        Optional<Livreur> livreur = livreurService.getLivreurById(id);
        return livreur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint pour obtenir uniquement la localisation d'un livreur par son ID
    @GetMapping("/{id}/location")
    public ResponseEntity<LivreurDTO> getLivreurLocation(@PathVariable Long id) {
        Optional<Livreur> livreur = livreurService.getLivreurById(id);
        if (livreur.isPresent()) {
            LivreurDTO livreurDTO = new LivreurDTO();
            livreurDTO.setLatitude(livreur.get().getLatitude());
            livreurDTO.setLongitude(livreur.get().getLongitude());
            return ResponseEntity.ok(livreurDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint pour mettre à jour la localisation d'un livreur
    @PutMapping("/{id}/updateLocation")
    public ResponseEntity<Livreur> updateLocation(@PathVariable Long id, @RequestBody LivreurDTO livreurDTO) {
        Optional<Livreur> updatedLivreur = livreurService.updateLocation(id, livreurDTO);
        return updatedLivreur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint pour supprimer un livreur par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLivreur(@PathVariable Long id) {
        livreurService.deleteLivreur(id);
        return ResponseEntity.ok("Livreur supprimé avec succès.");
    }
}
