package com.example.haccpbackend.modulSuiviHuile;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
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
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public Page<SuiviHuiles> findAllFriteuses(Pageable pageable) {


        return suiviHuileRepository.findAllByOrderByIdDesc(pageable);

    }


    @GetMapping("/huileDejour")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<?> findFriteuseDeJour() {

        List<SuiviHuiles> friteuseDeJour = iServiceSuiviHuile.findFriteuseDeJour();

        if (friteuseDeJour.isEmpty()) {

            return ResponseEntity.ok(Collections.emptyList());
        } else {

            return ResponseEntity.ok(friteuseDeJour);

        }
    }


    @GetMapping("/findByDate/{date}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    public ResponseEntity<?> findFristeuseByDate(@PathVariable LocalDate date) {


        List<SuiviHuiles> friteuseByDate = iServiceSuiviHuile.findFriteuseByDate(date);


        if (friteuseByDate.isEmpty()) {

            return ResponseEntity.ok(Collections.emptyList());
        } else {

            return ResponseEntity.ok(friteuseByDate);

        }


    }


    // Modifier un friteuse
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<SuiviHuiles> updateFriteuse(@PathVariable Long id, @RequestBody SuiviHuiles newFriteuse) {

        return ResponseEntity.ok(iServiceSuiviHuile.updateFriteuse(id, newFriteuse));


    }


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<SuiviHuiles> createFriteuse(@Valid @RequestBody SuiviHuiles friteuse) {

        SuiviHuiles friteuse1 = iServiceSuiviHuile.createFruiteuse(friteuse);


        if (friteuse1 == null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        } else {

            return ResponseEntity.status(HttpStatus.CREATED).body(friteuse1);
        }


    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    @Transactional
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteFriteuse(@PathVariable Long id) {


        try {

            iServiceSuiviHuile.deleteFriteuse(suiviHuileRepository.findById(id).get());

            return ResponseEntity.ok().build();


        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }


    @PutMapping(value = "/validateHuile/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<SuiviHuiles> validateHuile(@PathVariable Long id
            , @RequestPart("huile") String suiviHuileJson , @RequestPart(value = "file", required = false) MultipartFile file) {

        System.out.println("ID reçu dans le controller: " + id);

        try {



            System.out.println(suiviHuileJson);



            // Convertir le JSON en objet suiviHuileDTO
            ObjectMapper objectMapper = new ObjectMapper();
            SuiviHuileDto suiviHuileDto = objectMapper.readValue(suiviHuileJson, SuiviHuileDto.class);




            // Appeler le service pour mettre à jour le friteuse
            SuiviHuiles suiviHuiles1 = serviceSuiviHuile.validateHuile(id, suiviHuileDto, file);

            return ResponseEntity.ok(suiviHuiles1);


        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }









    @GetMapping("/imageAfter/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<byte[]> getFriteuseImage(@PathVariable Long id) {

        SuiviHuiles suiviHuiles= suiviHuileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Friteuse non trouvé"));


        byte[] image = suiviHuiles.getImageOfFriteuseAfter();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaTypeFactory.getMediaType("image.jpg")
                .orElse(MediaType.APPLICATION_OCTET_STREAM));
        headers.setContentLength(image.length);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }





    @GetMapping("/AllSuiviHuiles/{email}/rapportTableByDate")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> findAllSuiviHuileGetRapportPdfTableByDate(
            @PathVariable String email,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
            throws IOException {

        // 1. Récupérer tous les Friteuse
        List<SuiviHuiles>   suiviHuiles = suiviHuileRepository.findAll();

        // 2. Filtrer par date exacte
        List<SuiviHuiles> suiviHuilesFiltre = suiviHuiles.stream()
                .filter(n -> n.getCreatedDay() != null && n.getCreatedDay().equals(date))
                .toList();

        // 3. Vérifier si la liste est vide
        if (suiviHuilesFiltre.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        // 4. Générer le PDF
        byte[] pdfBytes = serviceSuiviHuile.generatePdfReportTable(
                suiviHuilesFiltre,
                  date.toString()
        );

        // 5. Envoyer Email
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(pdfBytes);

            serviceSuiviHuile.sendEmailWithPdf(
                    email,
                    "Rapport Suivi Huile Friteuse - " + date,
                    "Bonjour , Veuillez trouver ci-joint le rapport de suivi d'huile des Friteuse à la date : " + date,
                    baos
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'envoi de l'email.");
        }

        // 6. Retourner le PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "rapport_Suivi_Huile_" + date + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }




    @GetMapping("/AllSuiviHuile/{email}/rapportTableBetweenDates")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> findAllSuiviHuileGetRapportPdfTableBetweenDates(
            @PathVariable String email,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) throws IOException {

        // 1. Récupérer tous les Friteuse
        List<SuiviHuiles>   suiviHuiles = suiviHuileRepository.findAll();

        // 2. Filtrer ceux entre startDate et endDate inclus
        List<SuiviHuiles> suiviHuileFiltre = suiviHuiles.stream()
                .filter(n -> {
                    LocalDate createdDay = n.getCreatedDay();
                    return createdDay != null &&
                            (createdDay.isEqual(startDate) || createdDay.isAfter(startDate)) &&
                            (createdDay.isEqual(endDate) || createdDay.isBefore(endDate));
                })
                .toList();

        // 3. Vérifier si vide
        if (suiviHuileFiltre.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        // 4. Générer le PDF
        byte[] pdfBytes = serviceSuiviHuile.generatePdfReportTable(
                suiviHuileFiltre,
                "du " + startDate + " au " + endDate
        );

        // 5. Envoyer Email
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(pdfBytes);

            serviceSuiviHuile.sendEmailWithPdf(
                    email,
                    "Rapport Suivi Huile Friteuse - du " + startDate + " au " + endDate,
                    " Bonjour , Veuillez trouver ci-joint le rapport de Suivi D'huile entre " + startDate + " et " + endDate,
                    baos
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'envoi de l'email.");
        }

        // 6. Retourner le PDF en HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "attachment",
                "rapport_Suivi_Huile_" + startDate + "_to_" + endDate + ".pdf"
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }



















}







