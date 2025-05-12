package com.example.haccpbackend.modulPlanning;


import com.example.haccpbackend.nettoyagesPostes.CategorieNettoyage;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PlanningCategorie> createPlanningCategorie(@Valid @RequestBody PlanningCategorie planningCategorie){

        return ResponseEntity.status(HttpStatus.CREATED).body(planningCategorieRepository.save(planningCategorie));

    }





    @GetMapping("/findAllCategories")
    public ResponseEntity<List<PlanningCategorie>> findAllPlanningCategorie(){

        List<PlanningCategorie> planningCategories=planningCategorieRepository.findAll();

        if (planningCategories.isEmpty()){

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(planningCategories);

    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletePlanningCategorie(@PathVariable Long id){


        try {

            planningCategorieRepository.delete(planningCategorieRepository.findById(id).get());
            return ResponseEntity.noContent().build();

        } catch (Exception e){

            return ResponseEntity.notFound().build();

        }



    }





}
