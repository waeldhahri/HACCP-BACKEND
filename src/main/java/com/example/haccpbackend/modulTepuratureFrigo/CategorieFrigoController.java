package com.example.haccpbackend.modulTepuratureFrigo;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/categoriesFrigo")
public class CategorieFrigoController {


        private final CategorieFrigoService categorieFrigoService;
        private final IServiceCategorieFrigo iServiceCategorieFrigo;
        private final CategorieFrigoRepository categorieFrigoRepository;


    public CategorieFrigoController(CategorieFrigoService categorieFrigoService, IServiceCategorieFrigo iServiceCategorieFrigo, CategorieFrigoRepository categorieFrigoRepository) {
        this.categorieFrigoService = categorieFrigoService;
        this.iServiceCategorieFrigo = iServiceCategorieFrigo;
        this.categorieFrigoRepository = categorieFrigoRepository;
    }



    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategorieFrigo> createCategorieFrigo(@Valid @RequestBody CategorieFrigo categorieFrigo){


        return ResponseEntity.status(HttpStatus.CREATED).body(categorieFrigoService.createCategorieFrigo(categorieFrigo));

    }


    @GetMapping("/findCategorieByName/{nameCategorie}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<CategorieFrigo>> findCategorieFrigoByName(@PathVariable String nameCategorie){

        List<CategorieFrigo> categorieFrigo=categorieFrigoService.findCategorieFrigoByname(nameCategorie);





        if (categorieFrigo.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }

        return ResponseEntity.ok(categorieFrigo);
    }






    @GetMapping("/findAllCategories")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<CategorieFrigo>> findAllCategorieFrigo(){

        List<CategorieFrigo> categorieFrigos=categorieFrigoService.findAllCategoriesFrigo();

        if (categorieFrigos.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(categorieFrigos);

    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public ResponseEntity<Void> deleteCategorieFrigo(@PathVariable Long id) {

        Optional<CategorieFrigo> categorieFrigoOptional = categorieFrigoRepository.findById(id);

        if (categorieFrigoOptional.isPresent()) {
            iServiceCategorieFrigo.deleteCategorieFrigo(categorieFrigoOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }






}
