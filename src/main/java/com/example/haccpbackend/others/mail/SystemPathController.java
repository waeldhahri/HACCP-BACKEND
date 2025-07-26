package com.example.haccpbackend.others.mail;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/api/system")
public class SystemPathController {

    @GetMapping("/path")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> getApplicationPath() {
        try {
            // Récupère le chemin absolu de l'application (le dossier où le .jar est lancé)
            String currentPath = new File("").getAbsolutePath();
            return ResponseEntity.ok(currentPath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la récupération du chemin : " + e.getMessage());
        }
    }
}