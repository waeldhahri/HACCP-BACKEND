package com.example.haccpbackend.modulPlanning;


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
@RequestMapping("/api/categoriePlanning")
public class PlanningCategorieController {





    private final PlanningCategorieRepository planningCategorieRepository;


    public PlanningCategorieController(PlanningCategorieRepository planningCategorieRepository) {
        this.planningCategorieRepository = planningCategorieRepository;
    }




    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PlanningCategorie> createPlanningCategorie(@Valid @RequestBody PlanningCategorie planningCategorie){

        return ResponseEntity.status(HttpStatus.CREATED).body(planningCategorieRepository.save(planningCategorie));

    }





    @GetMapping("/findAllCategories")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> findAllPlanningCategorie(){

        List<PlanningCategorie> planningCategories=planningCategorieRepository.findAll();

        if (planningCategories.isEmpty()){

            return ResponseEntity.ok(Collections.emptyMap());
        }

        return ResponseEntity.ok(planningCategories);

    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public ResponseEntity<Void> deletePlanningCategorie(@PathVariable Long id){


        try {

            planningCategorieRepository.delete(planningCategorieRepository.findById(id).get());
            return ResponseEntity.ok().build();

        } catch (Exception e){

            return ResponseEntity.notFound().build();

        }



    }





}
