package com.example.haccpbackend.modulTepuratureFrigo;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/frigo")
public class FrigoController {





    private final FrigoService frigoService;


    public FrigoController(FrigoService frigoService) {
        this.frigoService = frigoService;
    }




    @GetMapping("/Categorie/{categorieName}")
    public ResponseEntity<List<Frigo>> findFrigoByCategorie(@PathVariable String categorieName){


        List<Frigo> frigos = frigoService.findFrigoByCategorie(categorieName);

        if (frigos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.ok(frigos);

     /*   return frigoService.findFrigoByCategorie(categorieName).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());*/

    }






}
