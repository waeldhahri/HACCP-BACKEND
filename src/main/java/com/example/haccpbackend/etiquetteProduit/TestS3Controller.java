package com.example.haccpbackend.etiquetteProduit;

import com.example.haccpbackend.s3.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/s3/test")
public class TestS3Controller {

    private final S3Service s3Service;

    public TestS3Controller(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @DeleteMapping("/supprimer-anciennes-images")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<String> supprimerImagesAnciennesManuellement() {
        s3Service.supprimerImagesAnciennes();
        return ResponseEntity.ok("Suppression des images de plus d'un an lancée.");
    }
}