package com.example.haccpbackend.etiquetteProduit;

import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;


@Entity
public class CategorieProduit {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;


    @NotNull
    @NotEmpty
    @Column(unique = true , nullable = false)
    private String name;



    @OneToMany(mappedBy = "categorieProduit", cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnore
    private List<Produit> produits = new ArrayList<>();












}
