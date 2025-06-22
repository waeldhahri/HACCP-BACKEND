package com.example.haccpbackend.modulTepuratureFrigo;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaTypeFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/api/frigo")
public class FrigoController {




    @Autowired
    private ObjectMapper objectMapper;


    private final FrigoService frigoService;

    private final FrigoRepository frigoRepository;

    private final CategorieFrigoRepository categorieFrigoRepository;

    private final IServiceFrigo iServiceFrigo;


    public FrigoController(FrigoService frigoService, FrigoRepository frigoRepository,
                           CategorieFrigoRepository categorieFrigoRepository , ObjectMapper objectMapper, IServiceFrigo iServiceFrigo) {
        this.frigoService = frigoService;
        this.frigoRepository = frigoRepository;
        this.categorieFrigoRepository = categorieFrigoRepository;
        this.objectMapper = objectMapper;
        this.iServiceFrigo = iServiceFrigo;
    }





    @GetMapping("/page")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public Page<Frigo> findAllFrigos(Pageable pageable){

        return frigoRepository.findAllByOrderByIdDesc(pageable);

    }

    @GetMapping("/categorie/{categorieName}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public ResponseEntity<?> findFrigoByCategorie(@PathVariable String categorieName){


        List<Frigo> frigos = frigoService.findFrigoByCategorie(categorieName);

        if (frigos.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyMap());
        }

        return ResponseEntity.ok(frigos);


    }



    @PostMapping(value = "/add" , /*consumes = MediaType.MULTIPART_FORM_DATA_VALUE*/ consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public ResponseEntity<Frigo> createFrigo(
            @RequestPart("frigo") String frigoJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {


        @Valid
        FrigoRequest frigoRequest =  objectMapper.readValue(frigoJson, FrigoRequest.class);




        // Récupère la catégorie
        CategorieFrigo categorie = categorieFrigoRepository.findById(frigoRequest.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));


        try {

            @Valid
            // Crée le frigo
            Frigo frigo = new Frigo();
            frigo.setName(frigoRequest.getName());
            frigo.setCategorie(categorie);

            if (frigoRepository.findFirstByNameAndCategorieFrigo(frigo.getName(), frigo.getCategorieFrigo()).isPresent()) {
                throw new RuntimeException("Un frigo avec ce nom existe déjà dans cette catégorie.");
            }

            Frigo savedFrigo = frigoRepository.save(frigo);




            // Gérer l'image
            if (imageFile != null) {

                frigo.setImageOfFrigo(imageFile.getBytes());


                // Générer l'URL complète de l'image
                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/frigo/")
                        .path("/image/")
                        .path(frigo.getId().toString())
                        .toUriString();


                frigo.setImageUrl(imageUrl);



            } else

            { frigo.setImageOfFrigo(null);
                frigo.setImageUrl(null);
            }





            // Sauvegarde
            Frigo saved = frigoRepository.save(frigo);

            return ResponseEntity.status(HttpStatus.CREATED).body(saved);


        }catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }


    @GetMapping("/image/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<byte[]> getFrigoImage(@PathVariable Long id) {
        Frigo frigo = frigoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Frigo non trouvé"));

        byte[] image = frigo.getImageOfFrigo();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaTypeFactory.getMediaType("image.jpg")
                .orElse(MediaType.APPLICATION_OCTET_STREAM));
        headers.setContentLength(image.length);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }







    @PutMapping(value = "/update/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public ResponseEntity<Frigo> updateFrigo(@PathVariable Long id
            , @RequestPart("frigo") String frigoJson , @RequestPart(value = "file", required = false) MultipartFile file)

    {




        try {


            System.out.println(frigoJson);



            // Convertir le JSON en objet ProductDTO
            ObjectMapper objectMapper = new ObjectMapper();
            FrigoRequest frigoRequest = objectMapper.readValue(frigoJson, FrigoRequest.class);

            // Appeler le service pour mettre à jour le produit
            Frigo updatedFrigo = iServiceFrigo.updateFrigo(id,frigoRequest,file);


            return ResponseEntity.ok(updatedFrigo);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }



    }




    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
   // @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<Void> deleteFrigo(@PathVariable Long id){


        try {

            iServiceFrigo.deleteFrigo(frigoRepository.findById(id).get());
            return ResponseEntity.ok().build();

        } catch (Exception e){

           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }













}
