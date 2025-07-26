package com.example.haccpbackend.etiquetteProduit;


import com.example.haccpbackend.nettoyagesPostes.NettoyagesPoste;
import com.example.haccpbackend.s3.S3Service;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/produits")
public class ProduitController {



    private final ProduitRepository produitRepository;

    private final S3Service s3Service;

    private final CategorieProduitRepository categorieProduitRepository;




    public ProduitController(ProduitRepository produitRepository, S3Service s3Service, CategorieProduitRepository categorieProduitRepository) {
        this.produitRepository = produitRepository;
        this.s3Service = s3Service;
        this.categorieProduitRepository = categorieProduitRepository;
    }




    @PostMapping(value = "/add" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Produit> ajouterProduit(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("produitname") String nomProduit,
            @RequestParam("quantite") Double quantite,
            @RequestParam("dlc") boolean dlc,
            @RequestParam("categorieId") Long categorieId
    ) {

        CategorieProduit categorie = categorieProduitRepository.findById(categorieId)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

        String urlS3 = s3Service.uploadFile(photo, "produits/" + photo.getOriginalFilename());



        Produit produit = new Produit();
        produit.setProduitname(nomProduit);
        produit.setQuantite(quantite);
        produit.setDlc(dlc);
        produit.setCategorieProduit(categorie);
        produit.setPhotoUrl(urlS3);
        produit.setDateDeStockage(LocalDate.now());

        return ResponseEntity.ok(produitRepository.save(produit));
    }







    @GetMapping("/photo-by-date")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> getPhotoUrl(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        List<Produit> produits = produitRepository.findAllByDateDeStockage(date);

        if (produits.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<String> photoUrls = produits.stream()
                .map(Produit::getPhotoUrl)
                .filter(Objects::nonNull)
                .toList();

        return ResponseEntity.ok(photoUrls);
    }






    @GetMapping("/categorie/{categorieName}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> findProduitByCategorie(@PathVariable String categorieName){



        CategorieProduit categorie = categorieProduitRepository.findByNameIgnoreCase(categorieName)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));


        List<Produit> produits=produitRepository.findByCategorieProduit_NameIgnoreCase(categorieName);


        if (produits.isEmpty()) {

            return ResponseEntity.ok(Collections.emptyList());

        }

        return ResponseEntity.ok(produits);


    }



    @PutMapping(value = "/{id}/ouverture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Produit> ajouterDateEtPhotoOuverture(
            @PathVariable Long id,
            @RequestParam("photoOuverture") MultipartFile photoOuverture
    ) {
        // 1. Trouver le produit
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé  "));

        // 2. Uploader la photo sur S3
        String urlS3 = s3Service.uploadFile(photoOuverture, "produits/" + photoOuverture.getOriginalFilename());

        // 3. Mettre à jour les champs
        produit.setDateDeOuverture(LocalDate.now());
        produit.setPhotoDeOuvertureUrl(urlS3);

        // 4. Sauvegarder
        Produit updatedProduit = produitRepository.save(produit);

        return ResponseEntity.ok(updatedProduit);
    }

}
