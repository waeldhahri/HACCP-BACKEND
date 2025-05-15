package com.example.haccpbackend.nettoyagesPostes;


import com.example.haccpbackend.modulPlanning.Planning;
import com.example.haccpbackend.modulPlanning.PlanningCategorie;
import com.example.haccpbackend.modulTepuratureFrigo.CategorieFrigo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nettoyageposte")
@CrossOrigin
public class NettoyagePosteController {


            private final NettoyagePosteRepository nettoyagePosteRepository;

            private final ServiceNettoyagePoste serviceNettoyagePoste;

            private final IServiceNettoyagePoste iServiceNettoyagePoste;

            @Autowired
            private ObjectMapper objectMapper;

            private final CategorieNettoyageRepository categorieNettoyageRepository;

    public NettoyagePosteController(NettoyagePosteRepository nettoyagePosteRepository,
                                    ServiceNettoyagePoste serviceNettoyagePoste, IServiceNettoyagePoste iServiceNettoyagePoste,
                                    ObjectMapper objectMapper, CategorieNettoyageRepository categorieNettoyageRepository) {
        this.nettoyagePosteRepository = nettoyagePosteRepository;
        this.serviceNettoyagePoste = serviceNettoyagePoste;
        this.iServiceNettoyagePoste = iServiceNettoyagePoste;
        this.objectMapper = objectMapper;
        this.categorieNettoyageRepository = categorieNettoyageRepository;
    }







    @GetMapping("/page")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public Page<NettoyagesPoste> findAllNettoyagesPoste(Pageable pageable){

        return nettoyagePosteRepository.findAllByOrderByIdDesc(pageable);


    }






    @GetMapping("/categorie/{categorieName}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<NettoyagesPoste>> findNettoyagesPosteByCategorie(@PathVariable String categorieName){

        List<NettoyagesPoste> nettoyagesPostes=serviceNettoyagePoste.findNettoyagesPosteByCategorie(categorieName);


        if (nettoyagesPostes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(nettoyagesPostes);


    }

    @PostMapping(value = "/addPoste")
    @PreAuthorize("hasAuthority('ADMIN')")
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










/*




            @PostMapping(value = "/add" , */
/*consumes = MediaType.MULTIPART_FORM_DATA_VALUE*//*
 consumes = {"multipart/form-data"})
            public ResponseEntity<NettoyagesPoste> createPoste(
            @RequestPart("poste") String posteJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {



        @Valid
        NettoyagePosteRequest nettoyagePosteRequest=objectMapper.readValue(posteJson,NettoyagePosteRequest.class);

        // Récupère la catégorie
        CategorieNettoyage categorieNettoyage = categorieNettoyageRepository.findById(nettoyagePosteRequest.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));



        try {

            @Valid
            // Crée le poste
            NettoyagesPoste nettoyagesPoste = new NettoyagesPoste();
            nettoyagesPoste.setNameOfPoste(nettoyagePosteRequest.getName());
            nettoyagesPoste.setCategorieNettoyage(categorieNettoyage);
            nettoyagesPoste.setNote(nettoyagesPoste.getNote());
            nettoyagesPoste.setValidePar(nettoyagesPoste.getValidePar());
            nettoyagesPoste.setValide(false);
            nettoyagesPoste.setLastModifiedTime(null);
            nettoyagesPoste.setLastModifiedDay(null);
            nettoyagesPoste.setValidAt(null);


            NettoyagesPoste savedposte = nettoyagePosteRepository.save(nettoyagesPoste);




            // Gérer l'image
            if (imageFile != null) {

                nettoyagesPoste.setImageOfPosteBefore(imageFile.getBytes());


                // Générer l'URL complète de l'image
                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/nettoyageposte/")
                        .path("/image/")
                        .path(nettoyagesPoste.getId().toString())
                        .toUriString();


                nettoyagesPoste.setImageBeforeUrl(imageUrl);



            } else

            { nettoyagesPoste.setImageOfPosteBefore(null);
                nettoyagesPoste.setImageBeforeUrl(null);
            }

            // Sauvegarde
            NettoyagesPoste saved = nettoyagePosteRepository.save(nettoyagesPoste);





            return ResponseEntity.status(HttpStatus.CREATED).body(saved);


        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }


*/












    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){


        try {

            iServiceNettoyagePoste.deleteNettoyagePoste(nettoyagePosteRepository.findById(id).get());
            return ResponseEntity.noContent().build();

        } catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }




    @PutMapping(value = "/validatePost/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<NettoyagesPoste> validatePost(@PathVariable Long id
            , @RequestPart("poste") String posteJson , @RequestPart(value = "file1", required = false) MultipartFile file1 ,
                                                        @RequestPart(value = "file2", required = false) MultipartFile file2)

    {

        System.out.println("ID reçu dans le controller: " + id);

        try {


            System.out.println(posteJson);



            // Convertir le JSON en objet ProductDTO
            ObjectMapper objectMapper = new ObjectMapper();
            NettoyagePosteRequest nettoyagePosteRequest = objectMapper.readValue(posteJson, NettoyagePosteRequest.class);


            // Appeler le service pour mettre à jour le poste
            NettoyagesPoste updatedPoste = iServiceNettoyagePoste.validatePosteNettoyage(id,nettoyagePosteRequest,file1,file2);

            return ResponseEntity.ok(updatedPoste);


        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }



    }



    @GetMapping("/imageBefore/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<byte[]> getPosteBeforeImage(@PathVariable Long id) {

        NettoyagesPoste nettoyagesPoste= nettoyagePosteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poste non trouvé"));


        byte[] image = nettoyagesPoste.getImageOfPosteBefore();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaTypeFactory.getMediaType("image.jpg")
                .orElse(MediaType.APPLICATION_OCTET_STREAM));
        headers.setContentLength(image.length);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }


    @GetMapping("/imageAfter/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<byte[]> getPosteAfterImage(@PathVariable Long id) {

        NettoyagesPoste nettoyagesPoste= nettoyagePosteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poste non trouvé"));


        byte[] image = nettoyagesPoste.getImageOfPosteAfter();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaTypeFactory.getMediaType("image.jpg")
                .orElse(MediaType.APPLICATION_OCTET_STREAM));
        headers.setContentLength(image.length);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }



}
