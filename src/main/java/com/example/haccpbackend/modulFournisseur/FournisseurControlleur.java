package com.example.haccpbackend.modulFournisseur;


import com.example.haccpbackend.modulProducts.Product;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fournisseurs")
@CrossOrigin
public class FournisseurControlleur {


    @Autowired
    private FournisseurService fournisseurService;


    public FournisseurControlleur(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }







    @GetMapping("")
    public List<Fournisseur> getAllFournisseur(){

        return fournisseurService.getAllFourniseurs();

    }

    @PostMapping("")
    public ResponseEntity<Fournisseur> createFournisseur(@Valid @RequestBody Fournisseur fournisseur){

        return ResponseEntity.status(HttpStatus.CREATED).body(fournisseurService.addFournisseur(fournisseur));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> findFournisseurById(@PathVariable Long id){


        //Fournisseur fournisseur = fournisseurService.getFournisseurById(id);




        //return fournisseur.stream().map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());

        return ResponseEntity.ok(fournisseurService.getFournisseurById(id));

    }





    @DeleteMapping("/{id}")
    @Transactional
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Long id){

        //   tokenRepository.clearUserReferences(userId);

        //sessionRepository.clearEmployeeReferences(userId);


        fournisseurService.deleteFournisseur(fournisseurService.getFournisseurById(id));

        return ResponseEntity.noContent().build();

    }



    // Modifier un fournisseur
    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable Long id, @RequestBody Fournisseur fournisseur) {

        return ResponseEntity.ok(fournisseurService.updateFournisseur(id , fournisseur));


    }





    // Ajouter une interaction pour un fournisseur
    @PostMapping("/{id}/interactions")
    public ResponseEntity<FournisseurInteraction> addInteraction(@PathVariable Long id, @RequestBody FournisseurInteraction interaction) {

        return ResponseEntity.ok(fournisseurService.addInteraction(id, interaction));


    }



    // Récupérer l'historique des interactions d'un fournisseur
    @GetMapping("/{id}/interactions")
    public ResponseEntity<List<FournisseurInteraction>> getFournisseurInteractions(@PathVariable Long id) {

        return ResponseEntity.ok(fournisseurService.getFounisseurInteraction(id));

        //return ResponseEntity.ok(supplierService.getSupplierInteractions(id));
    }

}
