package com.example.haccpbackend.modulPlanning;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createPlanning(
            @Valid @RequestBody Planning planning,
            @RequestParam("categorieId") Long categorieId) {

        PlanningCategorie categorie = planningCategorieRepository.findById(categorieId)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

        planning.setPlanningCategorie(categorie);

        try {
            // Vérification avant insertion
            Optional<Planning> existing = planningRepository
                    .findFirstByTacheAndPlanningCategorieAndCreatedDay(
                            planning.getTache(),
                            planning.getPlanningCategorie(),
                            planning.getCreatedDay());

            if (existing.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Une tâche avec ce nom existe déjà dans cette catégorie pour cette date.");
            }

            Planning saved = planningService.createPlanning(planning);
            return ResponseEntity.ok(saved);

        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Une tâche avec ce nom existe déjà dans cette catégorie pour cette date (vérification base).");
        }
    }


    @GetMapping("/today")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Planning>> getPlanningsDuJour() {
        LocalDate today = LocalDate.now();
        List<Planning> plannings = planningRepository.findByCreatedDay(today);
        return ResponseEntity.ok(plannings);
    }




    @GetMapping("/by-day")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Planning>> getPlanningsParJour(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Planning> plannings = planningRepository.findByCreatedDay(date);

        if (plannings.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(plannings);
        }


    }






    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deletePlanning(@PathVariable Long id){


        try {

            planningRepository.delete(planningRepository.findById(id).get());
            return ResponseEntity.noContent().build();

        } catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


    }



}
