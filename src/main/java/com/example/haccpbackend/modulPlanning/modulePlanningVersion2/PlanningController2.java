package com.example.haccpbackend.modulPlanning.modulePlanningVersion2;


import com.example.haccpbackend.modulSuiviHuile.IServiceSuiviHuile;
import com.example.haccpbackend.modulSuiviHuile.SuiviHuileRepository;
import com.example.haccpbackend.modulSuiviHuile.SuiviHuiles;
import com.example.haccpbackend.modulTepuratureFrigo.*;
import com.example.haccpbackend.nettoyagesPostes.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/planning")
public class PlanningController2 {


    @Autowired
    private ObjectMapper objectMapper;

    private final FrigoRepository frigoRepository;
    private final NettoyagePosteRepository nettoyagePosteRepository;
    private final ServiceNettoyagePoste serviceNettoyagePoste;

    private final SuiviHuileRepository suiviHuileRepository;

    private final CategorieFrigoRepository categorieFrigoRepository;

    private final IServiceSuiviHuile iserviceSuiviHuile;

    private final CategorieNettoyageRepository categorieNettoyageRepository;

    private final IServiceNettoyagePoste iServiceNettoyagePoste;


    public PlanningController2(FrigoRepository frigoRepository,
                               NettoyagePosteRepository nettoyagePosteRepository,
                               ServiceNettoyagePoste serviceNettoyagePoste,
                               SuiviHuileRepository suiviHuileRepository,
                               CategorieFrigoRepository categorieFrigoRepository,
                               IServiceSuiviHuile iserviceSuiviHuile,
                               CategorieNettoyageRepository categorieNettoyageRepository,
                               IServiceNettoyagePoste iServiceNettoyagePoste) {
        this.frigoRepository = frigoRepository;
        this.nettoyagePosteRepository = nettoyagePosteRepository;
        this.serviceNettoyagePoste = serviceNettoyagePoste;
        this.suiviHuileRepository = suiviHuileRepository;
        this.categorieFrigoRepository = categorieFrigoRepository;
        this.iserviceSuiviHuile = iserviceSuiviHuile;
        this.categorieNettoyageRepository = categorieNettoyageRepository;
        this.iServiceNettoyagePoste = iServiceNettoyagePoste;
    }

    @GetMapping("/getAllPlannings")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<PlanningVersion2> getPlanning(){


        PlanningVersion2 planningVersion2 = new PlanningVersion2();
       planningVersion2.setFrigos(frigoRepository.findAll());
       planningVersion2.setNettoyagesPostes(nettoyagePosteRepository.findAll());
       planningVersion2.setSuivisHuiles(suiviHuileRepository.findAll());


       return ResponseEntity.ok(planningVersion2);


    }








/*

    @PostMapping(value = "/addPosteNettoyage")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> createPosteNettoyage(@Valid @RequestBody NettoyagesPoste nettoyagesPoste,
                                                  @RequestParam("categorieId") Long categorieId) {




        CategorieNettoyage categorieNettoyage = categorieNettoyageRepository.findById(categorieId)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

        nettoyagesPoste.setCategorieNettoyage(categorieNettoyage);

        try {
            Optional<NettoyagesPoste> existing = nettoyagePosteRepository
                    .findFirstByNameOfPosteAndCategorieNettoyageAndCreatedDay(
                            nettoyagesPoste.getNameOfPoste(),
                            nettoyagesPoste.getCategorieNettoyage(),
                            nettoyagesPoste.getCreatedDay());

            if (existing.isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Une poste avec ce nom existe déjà dans cette catégorie pour cette date.");
            }

            NettoyagesPoste saved = iServiceNettoyagePoste.createNettoyagePoste(nettoyagesPoste);
            return ResponseEntity.ok(saved);

        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Une poste avec ce nom existe déjà dans cette catégorie pour cette date (vérification base).");
        }
    }




*/





}
