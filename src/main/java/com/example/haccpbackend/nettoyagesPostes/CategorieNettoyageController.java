package com.example.haccpbackend.nettoyagesPostes;


import com.example.haccpbackend.handler.ExceptionResponse;
import com.example.haccpbackend.modulTepuratureFrigo.CategorieFrigo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/CategorieNettoyage")
public class CategorieNettoyageController {


    private final CategorieNettoyageRepository categorieNettoyageRepository;

    private final IServiceCategorieNettoyage iServiceCategorieNettoyage;


    public CategorieNettoyageController(CategorieNettoyageRepository categorieNettoyageRepository, IServiceCategorieNettoyage iServiceCategorieNettoyage) {
        this.categorieNettoyageRepository = categorieNettoyageRepository;

        this.iServiceCategorieNettoyage = iServiceCategorieNettoyage;
    }



    @PostMapping("")
    public ResponseEntity<CategorieNettoyage> createCategorieNettoyage(@Valid @RequestBody CategorieNettoyage categorieNettoyage){


        return ResponseEntity.status(HttpStatus.CREATED).body(iServiceCategorieNettoyage.createCategorieNettoyage(categorieNettoyage));

    }





    @GetMapping("/findAllCategories")
    public ResponseEntity<List<CategorieNettoyage>> findAllCategorieNettoyage(){

        List<CategorieNettoyage> categorieNettoyages=iServiceCategorieNettoyage.findAllCategoriesNettoyage();

        if (categorieNettoyages.isEmpty()){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(categorieNettoyages);

    }




    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteCategorieNettoyage(@PathVariable Long id){


        try {
            iServiceCategorieNettoyage.deleteCategoriesNettoyage(categorieNettoyageRepository.findById(id).get());
            return ResponseEntity.noContent().build();
        } catch (Exception e){

            return ResponseEntity.notFound().build();

        }




    }


}
