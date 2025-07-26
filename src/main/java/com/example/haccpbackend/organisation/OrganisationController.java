package com.example.haccpbackend.organisation;


import com.example.haccpbackend.modulTepuratureFrigo.CategorieFrigo;
import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import com.example.haccpbackend.modulTepuratureFrigo.FrigoRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/organisation")
@Transactional
public class OrganisationController {


    private final OrganisationService organisationService;

    private final OrganisationRepository organisationRepository;


    @Autowired
    private ObjectMapper objectMapper;


    public OrganisationController(OrganisationService organisationService, OrganisationRepository organisationRepository) {
        this.organisationService = organisationService;
        this.organisationRepository = organisationRepository;
    }





    @PostMapping(value = "/add" , /*consumes = MediaType.MULTIPART_FORM_DATA_VALUE*/ consumes = {"multipart/form-data"})
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<?> createOrganisation( @RequestPart("organisation") String organisationJson,
                                                 @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {


        @Valid
        Organisation organisation = objectMapper.readValue(organisationJson, Organisation.class);


        try {

            @Valid
            Organisation organisation1 = new Organisation();
            organisation1.setName(organisation.getName());




           Organisation savedOrganisation=organisationRepository.save(organisation1);





            // Gérer l'image
            if (imageFile != null) {

                organisation1.setImage(imageFile.getBytes());


                // Générer l'URL complète de l'image
                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/organisation/")
                        .path("/image/")
                        .path(organisation1.getId().toString())
                        .toUriString();


                organisation1.setImageUrl(imageUrl);



            } else

            { organisation1.setImage(null);
                organisation1.setImageUrl(null);
            }





            // Sauvegarde
            Organisation saved = organisationRepository.save(organisation1);

            return ResponseEntity.status(HttpStatus.CREATED).body(saved);


        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }










    }



    @GetMapping("/image/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<byte[]> getOrganisationImage(@PathVariable Long id) {


        Organisation organisation = organisationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organisation non trouvé"));

        byte[] image = organisation.getImage();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaTypeFactory.getMediaType("image.jpg")
                .orElse(MediaType.APPLICATION_OCTET_STREAM));
        headers.setContentLength(image.length);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }







    @GetMapping("/findOrganisationByName/{nameOrganisation}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")

    public ResponseEntity<?> findOrganisationByName(@PathVariable String nameOrganisation) {

        List<Organisation> organisations = organisationRepository.findByNameIgnoreCase(nameOrganisation);

        if (organisations.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<OrganisationDto> dtoList = organisations.stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(dtoList);
    }




/*

    @GetMapping("/findAllOrganisations")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @Transactional()
    public ResponseEntity<?> findAllOrganisations(){

        List<Organisation> organisations=organisationRepository.findAll();

        if (organisations.isEmpty()){

            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(organisations);

    }
*/

    /*
    @GetMapping("/findAllOrganisations2")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @Transactional()
    public ResponseEntity<?> findAllOrganisations2(){

        List<Organisation> organisations=organisationService.getAllorganisations();

        if (organisations.isEmpty()){

            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(organisations);

    }*/




    @GetMapping("/findAllOrganisations")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @Transactional()
    public ResponseEntity<?> findAllOrganisations3() {

        List<Organisation> organisations = organisationRepository.findAll();

        if (organisations.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<OrganisationDto> dtoList = organisations.stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(dtoList);
    }


    public OrganisationDto toDto(Organisation org) {
        OrganisationDto dto = new OrganisationDto();
        dto.setId(org.getId());
        dto.setName(org.getName());
        dto.setImageUrl(org.getImageUrl());
        return dto;
    }







    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<Void> deleteOrganisation(@PathVariable Long id) {

        Optional<Organisation> organisation=organisationRepository.findById(id);

        if (organisation.isPresent()) {
            organisationRepository.delete(organisation.get());

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @PutMapping(value = "/update/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<?> updateOrganisation(@PathVariable Long id,
                                                @RequestPart("organisation") @Valid String organisationJson,
                                                @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            // Rechercher l'organisation existante
            Organisation existing = organisationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Organisation non trouvée"));

            // Mapper le JSON en objet Organisation
            Organisation updatedData = objectMapper.readValue(organisationJson, Organisation.class);

            // Mise à jour du nom
            existing.setName(updatedData.getName());

            // Si une nouvelle image est fournie, la remplacer
            if (imageFile != null && !imageFile.isEmpty()) {
                existing.setImage(imageFile.getBytes());

                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/organisation/image/")
                        .path(existing.getId().toString())
                        .toUriString();

                existing.setImageUrl(imageUrl);
            }

            // Sinon, conserver l’image existante (aucun changement)

            Organisation updated = organisationRepository.save(existing);

            return ResponseEntity.ok(updated);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors de la lecture de l'image ou du JSON : " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }





}
