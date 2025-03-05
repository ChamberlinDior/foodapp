package com.fooddelivery.controller;

import com.fooddelivery.dto.LivreurDTO;
import com.fooddelivery.model.Livreur;
import com.fooddelivery.service.LivreurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/livreurs")
public class LivreurController {

    @Autowired
    private LivreurService livreurService;

    @PostMapping
    public ResponseEntity<Livreur> createLivreur(@RequestBody LivreurDTO livreurDTO) {
        Livreur savedLivreur = livreurService.saveLivreur(livreurDTO);
        return ResponseEntity.ok(savedLivreur);
    }

    @PostMapping("/login")
    public ResponseEntity<Livreur> loginLivreur(@RequestBody LivreurDTO loginRequest) {
        Optional<Livreur> livreur = livreurService.authenticate(loginRequest.getNom(), loginRequest.getMotDePasse());
        return livreur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());
    }

    @GetMapping
    public ResponseEntity<List<Livreur>> getAllLivreurs() {
        List<Livreur> livreurs = livreurService.getAllLivreurs();
        return ResponseEntity.ok(livreurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livreur> getLivreurById(@PathVariable Long id) {
        Optional<Livreur> livreur = livreurService.getLivreurById(id);
        return livreur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

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

    @PutMapping("/{id}/updateLocation")
    public ResponseEntity<Livreur> updateLocation(@PathVariable Long id, @RequestBody LivreurDTO livreurDTO) {
        Optional<Livreur> updatedLivreur = livreurService.updateLocation(id, livreurDTO);
        return updatedLivreur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Nouvel endpoint pour mettre à jour le deviceToken du livreur
    @PutMapping("/{id}/updateDeviceToken")
    public ResponseEntity<Livreur> updateDeviceToken(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String deviceToken = body.get("deviceToken");
        Optional<Livreur> livreurOpt = livreurService.getLivreurById(id);
        if (livreurOpt.isPresent()) {
            Livreur livreur = livreurOpt.get();
            livreur.setDeviceToken(deviceToken);
            // Sauvegarde via une méthode surchargée dans le service (voir ci-dessous)
            Livreur updatedLivreur = livreurService.saveLivreur(livreur);
            return ResponseEntity.ok(updatedLivreur);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLivreur(@PathVariable Long id) {
        livreurService.deleteLivreur(id);
        return ResponseEntity.ok("Livreur supprimé avec succès.");
    }
}
