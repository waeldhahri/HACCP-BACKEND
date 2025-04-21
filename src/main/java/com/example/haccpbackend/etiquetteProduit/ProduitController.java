package com.example.haccpbackend.etiquetteProduit;


import com.example.haccpbackend.s3.S3Service;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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
            @RequestParam("produit") String nomProduit,
            @RequestParam("quantite") Double quantite,
            @RequestParam("dlc") boolean dlc,
            @RequestParam("categorieId") Long categorieId
    ) {
        String urlS3 = s3Service.uploadFile(photo, "produits/" + photo.getOriginalFilename());

        CategorieProduit categorie = categorieProduitRepository.findById(categorieId)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

        Produit produit = new Produit();
        produit.setProduit(nomProduit);
        produit.setQuantite(quantite);
        produit.setDlc(dlc);
        produit.setCategorieProduit(categorie);
        produit.setPhotoUrl(urlS3);

        return ResponseEntity.ok(produitRepository.save(produit));
    }





}
