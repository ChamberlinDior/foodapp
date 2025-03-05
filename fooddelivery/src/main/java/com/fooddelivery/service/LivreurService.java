package com.fooddelivery.service;

import com.fooddelivery.dto.LivreurDTO;
import com.fooddelivery.model.Livreur;
import com.fooddelivery.repository.LivreurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LivreurService {

    @Autowired
    private LivreurRepository livreurRepository;

    public Livreur saveLivreur(LivreurDTO livreurDTO) {
        Livreur livreur = new Livreur();
        livreur.setNom(livreurDTO.getNom());
        livreur.setPrenom(livreurDTO.getPrenom());
        livreur.setNumeroTelephone(livreurDTO.getNumeroTelephone());
        livreur.setPhotoProfil(livreurDTO.getPhotoProfil());
        livreur.setLatitude(livreurDTO.getLatitude());
        livreur.setLongitude(livreurDTO.getLongitude());
        livreur.setMotDePasse(livreurDTO.getMotDePasse());
        // Le deviceToken sera mis à jour via l'endpoint dédié
        return livreurRepository.save(livreur);
    }

    // Méthode surchargée pour sauvegarder directement une entité Livreur
    public Livreur saveLivreur(Livreur livreur) {
        return livreurRepository.save(livreur);
    }

    public Optional<Livreur> authenticate(String nom, String motDePasse) {
        return livreurRepository.findByNomAndMotDePasse(nom, motDePasse);
    }

    public List<Livreur> getAllLivreurs() {
        return livreurRepository.findAll();
    }

    public Optional<Livreur> getLivreurById(Long id) {
        return livreurRepository.findById(id);
    }

    public void deleteLivreur(Long id) {
        livreurRepository.deleteById(id);
    }

    public Optional<Livreur> updateLocation(Long id, LivreurDTO livreurDTO) {
        Optional<Livreur> livreurOpt = livreurRepository.findById(id);
        livreurOpt.ifPresent(livreur -> {
            livreur.setLatitude(livreurDTO.getLatitude());
            livreur.setLongitude(livreurDTO.getLongitude());
            livreurRepository.save(livreur);
        });
        return livreurOpt;
    }
}
