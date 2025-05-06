package com.example.haccpbackend.modulPlanning;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/plannings")
public class PlanningController {



    private final PlanningService planningService;
    private final PlanningRepository planningRepository;

    private final PlanningCategorieRepository planningCategorieRepository;


    public PlanningController(PlanningService planningService, PlanningRepository planningRepository, PlanningCategorieRepository planningCategorieRepository) {
        this.planningService = planningService;
        this.planningRepository = planningRepository;
        this.planningCategorieRepository = planningCategorieRepository;
    }



    @PostMapping("/add")
    public ResponseEntity<Planning> createPlanning(@Valid @RequestBody Planning planning , @RequestParam("categorieId") Long categorieId){


        PlanningCategorie categorie= planningCategorieRepository.findById(categorieId).
                orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

        Planning saved = planningService.createPlanning(planning);


        return ResponseEntity.ok(saved);


    }




    @DeleteMapping("/{id}")
    @Transactional
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deletePlanning(@PathVariable Long id){


        try {

            planningRepository.delete(planningRepository.findById(id).get());
            return ResponseEntity.noContent().build();

        } catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }



}
