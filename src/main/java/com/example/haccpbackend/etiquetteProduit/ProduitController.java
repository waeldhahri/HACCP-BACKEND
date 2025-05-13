package com.example.haccpbackend.etiquetteProduit;


import com.example.haccpbackend.nettoyagesPostes.NettoyagesPoste;
import com.example.haccpbackend.s3.S3Service;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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
        produit.setProduitName(nomProduit);
        produit.setQuantite(quantite);
        produit.setDlc(dlc);
        produit.setCategorieProduit(categorie);
        produit.setPhotoUrl(urlS3);
        produit.setDateDeStockage(LocalDate.now());

        return ResponseEntity.ok(produitRepository.save(produit));
    }







    @GetMapping("/photo-by-date")
    public ResponseEntity<List<String>> getPhotoUrl(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        List<Produit> produits = produitRepository.findAllByDateDeStockage(date);

        if (produits.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<String> photoUrls = produits.stream()
                .map(Produit::getPhotoUrl)
                .filter(Objects::nonNull)
                .toList();

        return ResponseEntity.ok(photoUrls);
    }






    @GetMapping("/categorie/{categorieName}")
    public ResponseEntity<List<Produit>> findProduitByCategorie(@PathVariable String categorieName){



        CategorieProduit categorie = categorieProduitRepository.findByNameIgnoreCase(categorieName)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));


        List<Produit> produits=produitRepository.findByCategorieProduit_NameIgnoreCase(categorieName);


        if (produits.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        }

        return ResponseEntity.ok(produits);


    }

}
