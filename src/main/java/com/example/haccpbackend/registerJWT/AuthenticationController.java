package com.example.haccpbackend.registerJWT;


import com.example.haccpbackend.modulTepuratureFrigo.CategorieFrigo;
import com.example.haccpbackend.organisation.Organisation;
import com.example.haccpbackend.organisation.OrganisationRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;

@RestController
@RequestMapping("/auth")
@CrossOrigin

public class AuthenticationController {

    private final AuthenticationService service;
    private final OrganisationRepository organisationRepository;


    public AuthenticationController(AuthenticationService service, OrganisationRepository organisationRepository) {
        this.service = service;
        this.organisationRepository = organisationRepository;
    }




    @PostMapping(value = "/register" , consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> register(//@RequestBody @Valid RegistrationRequest request

                                      @RequestParam("fullname") String fullname,
                                      @RequestParam("email") String email,
                                      @RequestParam("motdepasse") String motdepasse,
                                      @RequestParam("role") String role,
                                      @RequestParam("organisationId") Long organisationId,
                                      @RequestPart(value = "image", required = false) MultipartFile imageFile) throws MessagingException, IOException {





        // Récupère la organisation
        Organisation organisation = organisationRepository.findById(organisationId)
                .orElseThrow(() -> new RuntimeException("Organisation non trouvée"));


        RegistrationRequest request= new RegistrationRequest();
        request.setEmail(email);
        request.setFullname(fullname);
        request.setMotdepasse(motdepasse);
        request.setRole(role);
        request.setOrganisation(organisation);




        if (imageFile != null && !imageFile.isEmpty()) {
            // Génère un nom unique pour l'image
            String imageName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

            // Dossier de destination local (par exemple dans /uploads/)
            Path imagePath = Paths.get("uploads/" + imageName);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, imageFile.getBytes());

            // Génère l'URL d'accès
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/user/image/")
                    .path(imageName)
                    .toUriString();

            request.setImageOfUser(imageFile.getBytes());
            request.setImageUrl(imageUrl);
        } else {
            request.setImageOfUser(null);
            request.setImageUrl(null);
        }









        service.register(request);

        return ResponseEntity.accepted().build();



    }


/*
    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        Path imagePath = Paths.get("uploads/").resolve(imageName).normalize();
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists()) {
            // Détecte automatiquement le type MIME
            String contentType = Files.probeContentType(imagePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // fallback générique
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/



    @PostMapping("/authenticate")
    @Transactional
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}
