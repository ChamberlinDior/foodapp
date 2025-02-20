package com.fooddelivery.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    // La valeur de 'file.upload-dir' est injectée depuis application.properties
    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory for file upload.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Nettoyer le nom de fichier
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";
        int index = originalFileName.lastIndexOf('.');
        if (index > 0) {
            fileExtension = originalFileName.substring(index);
        }
        String fileName = UUID.randomUUID().toString() + fileExtension;

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + fileName);
            }
            // Copier le fichier dans le répertoire cible (en remplaçant le fichier existant si nécessaire)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Ici, on retourne le nom du fichier stocké. Vous pouvez aussi construire une URL complète.
            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
