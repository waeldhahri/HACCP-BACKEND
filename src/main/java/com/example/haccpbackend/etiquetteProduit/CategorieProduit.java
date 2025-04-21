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


    public CategorieProduit(Long id, String name, List<Produit> produits) {
        this.id = id;
        this.name = name;
        this.produits = produits;
    }

    public CategorieProduit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
}
