package com.example.haccpbackend.ModulSuiviHuile;


import com.example.haccpbackend.modulFournisseur.Fournisseur;
import com.example.haccpbackend.nettoyagesPostes.NettoyagesPoste;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/suiviHuile")
public class SuiviHuileController {


    private final SuiviHuileRepository suiviHuileRepository;


    private final ServiceSuiviHuile serviceSuiviHuile;

    private final IServiceSuiviHuile iServiceSuiviHuile;


    @Autowired
    private ObjectMapper objectMapper;


    public SuiviHuileController(SuiviHuileRepository suiviHuileRepository, ServiceSuiviHuile serviceSuiviHuile,
                                IServiceSuiviHuile iServiceSuiviHuile, ObjectMapper objectMapper) {
        this.suiviHuileRepository = suiviHuileRepository;
        this.serviceSuiviHuile = serviceSuiviHuile;
        this.iServiceSuiviHuile = iServiceSuiviHuile;
        this.objectMapper = objectMapper;
    }





    @GetMapping("/page")
    @Transactional
    public Page<SuiviHuiles> findAllFriteuses(Pageable pageable){


        return suiviHuileRepository.findAllByOrderByIdDesc(pageable);

    }



    @GetMapping("/huileDejour")
    @Transactional
    public ResponseEntity<List<SuiviHuiles>> findFriteuseDeJour() {

        List<SuiviHuiles> friteuseDeJour = iServiceSuiviHuile.findFriteuseDeJour();

        if (friteuseDeJour.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {

            return ResponseEntity.ok(friteuseDeJour);

        }
    }


    @GetMapping("/findByDate/{date}")
    @Transactional
    public ResponseEntity<List<SuiviHuiles>> findFristeuseByDate(@PathVariable LocalDate date){


        List<SuiviHuiles> friteuseByDate = iServiceSuiviHuile.findFriteuseByDate(date);


        if (friteuseByDate.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {

            return ResponseEntity.ok(friteuseByDate);

        }




    }



    // Modifier un fournisseur
    @PutMapping("/{id}")
    public ResponseEntity<SuiviHuiles> updateFriteuse(@PathVariable Long id, @RequestBody SuiviHuiles newFriteuse) {

        return ResponseEntity.ok(iServiceSuiviHuile.updateFriteuse(id, newFriteuse));


    }
























    @PostMapping("/add")
    public ResponseEntity<SuiviHuiles> createFriteuse(@Valid @RequestBody SuiviHuiles friteuse){

        SuiviHuiles friteuse1 = iServiceSuiviHuile.createFruiteuse(friteuse);


        if (friteuse1 == null){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } else {

            return ResponseEntity.status(HttpStatus.CREATED).body(friteuse1);
        }


    }






    @DeleteMapping("/{id}")
    @Transactional
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteFriteuse(@PathVariable Long id){


        try {

            iServiceSuiviHuile.deleteFriteuse(suiviHuileRepository.findById(id).get());

            return ResponseEntity.noContent().build();


        } catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }





}
