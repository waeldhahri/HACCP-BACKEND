package com.example.haccpbackend.etiquetteProduit;


import com.example.haccpbackend.nettoyagesPostes.CategorieNettoyage;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/categorieproduit")
public class CategorieProduitContoller {



    private final CategorieProduitRepository categorieProduitRepository;


    public CategorieProduitContoller(CategorieProduitRepository categorieProduitRepository) {
        this.categorieProduitRepository = categorieProduitRepository;
    }






    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<CategorieProduit> createCategorieProduit(@Valid @RequestBody CategorieProduit categorieProduit){


        return ResponseEntity.status(HttpStatus.CREATED).body(categorieProduitRepository.save(categorieProduit));

    }





    @GetMapping("/findAllCategories")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> findAllCategorieProduit(){

        List<CategorieProduit> categorieProduits=categorieProduitRepository.findAll();

        if (categorieProduits.isEmpty()){

            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(categorieProduits);

    }









    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<Void> deleteCategorieProduit(@PathVariable Long id){


        try {

            categorieProduitRepository.delete(categorieProduitRepository.findById(id).get());
            return ResponseEntity.ok().build();

        } catch (Exception e){

            return ResponseEntity.notFound().build();

        }




    }




}
