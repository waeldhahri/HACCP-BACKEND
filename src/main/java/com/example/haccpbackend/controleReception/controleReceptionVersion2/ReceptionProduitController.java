package com.example.haccpbackend.controleReception.controleReceptionVersion2;

import com.example.haccpbackend.controleReception.Product;
import com.example.haccpbackend.modulFournisseur.Fournisseur;
import com.example.haccpbackend.modulFournisseur.FournisseurRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/receptionProduit")
public class ReceptionProduitController {




    private final FournisseurRepository fournisseurRepository;

    private final ReceptionProduitService receptionProduitService;

    private final ReceptionProduitRepository receptionProduitRepository;

    @Autowired
    private ObjectMapper objectMapper;


    public ReceptionProduitController(FournisseurRepository fournisseurRepository,
                                      ReceptionProduitService receptionProduitService,
                                      ReceptionProduitRepository receptionProduitRepository,
                                      ObjectMapper objectMapper) {
        this.fournisseurRepository = fournisseurRepository;
        this.receptionProduitService = receptionProduitService;
        this.receptionProduitRepository = receptionProduitRepository;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/addReceptionProduit", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<ReceptionProduit> createReceptionProduit(
            @RequestPart("reception") String receptionJson,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        try {
            // Convertir JSON en objet DTO
            ReceptionProduitDTO dto = objectMapper.readValue(receptionJson, ReceptionProduitDTO.class);

            // Récupérer le fournisseur
            Fournisseur fournisseur = fournisseurRepository.findById(dto.getFournisseurId())
                    .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));


            // Créer l’objet
            ReceptionProduit reception = new ReceptionProduit();
            reception.setNote(dto.getNote());
            reception.setFournisseurs(fournisseur);
            reception.setProduitName(dto.getProduitName());

            ReceptionProduit saved1 = receptionProduitRepository.save(reception);

            // Si image présente
            if (file != null && !file.isEmpty()) {
                reception.setImageOfProduct(file.getBytes());

                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/receptionProduit/")
                        .path(reception.getIdProduit().toString())
                        .path("/image")
                        //.path(UUID.randomUUID().toString()) // ou id plus tard
                        .toUriString();

                reception.setImageUrl(imageUrl);
            } else {
                reception.setImageOfProduct(null);
                reception.setImageUrl(null);
            }

            // Sauvegarder
            ReceptionProduit saved = receptionProduitRepository.save(reception);

            return ResponseEntity.status(HttpStatus.OK).body(saved);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



    @GetMapping("/{id}/image")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {

        Optional<ReceptionProduit> receptionProduit =receptionProduitRepository.findById(id);

        if (receptionProduit.isEmpty() || receptionProduit.get().getImageOfProduct() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(receptionProduit.get().getImageOfProduct());
    }


    @GetMapping("/page")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public Page<ReceptionProduit> getAllProduct(Pageable pageable){

        return receptionProduitRepository.findAllByOrderByIdProduitDesc(pageable);

    }





    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> findProductById(@PathVariable Long id){


        Optional<ReceptionProduit> optionalProduit = receptionProduitRepository.findById(id);

        if (optionalProduit.isEmpty()) {
            // Retourner une liste vide : []
            return ResponseEntity.ok(Collections.emptyList());
        }

        // Extraire l'objet ReceptionProduit de l'Optional
        ReceptionProduit receptionProduit = optionalProduit.get();

        // Générer l'URL complète de l'image
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/products/")
                .path(receptionProduit.getIdProduit().toString())
                .path("/image")
                .toUriString();


        receptionProduit.setImageUrl(imageUrl);

        return ResponseEntity.ok(receptionProduit);



        // return ResponseEntity.ok(iServiceProduct.findproductById(id));
    }


    @GetMapping("/by-fournisseur/{fournisseurId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<?> getProductsByFournisseur(@PathVariable Long fournisseurId){


        List<ReceptionProduit> receptionProduits = receptionProduitRepository.findByFournisseursId(fournisseurId);




        if (receptionProduits.isEmpty()) {
            // Retourner HTTP 200 avec un JSON vide : {}
            return ResponseEntity.ok(Collections.emptyList());
        }else {

            return ResponseEntity.ok(receptionProduits);
        }




    }



    @GetMapping("/date/{date}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<?> findProductBydate(@PathVariable String date){



        // Conversion de String → LocalDate avec le format dd-MM-yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        List<ReceptionProduit> receptionProduits=receptionProduitRepository.findByDateReception(localDate);



        if (receptionProduits.isEmpty()) {
            // Retourner HTTP 200 avec un JSON vide : {}
            return ResponseEntity.ok(Collections.emptyList());
        }else {


            return ResponseEntity.ok(receptionProduits);

        }


    }





    @GetMapping("/produit/{produit}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<?> findProductByProduit(@PathVariable String produit){


        List<ReceptionProduit> produits = receptionProduitRepository.findByProduitName(produit);



        if (produits.isEmpty()) {
            // Retourner HTTP 200 avec un JSON vide : {}
            return ResponseEntity.ok(Collections.emptyList());
        }else {

            return ResponseEntity.ok(produits);
        }



    }




    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        try {
            Optional<ReceptionProduit> optionalProduit = receptionProduitRepository.findById(id);

            if (optionalProduit.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            receptionProduitRepository.delete(optionalProduit.get());
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }




}
