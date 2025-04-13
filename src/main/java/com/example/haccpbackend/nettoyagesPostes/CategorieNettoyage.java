package com.example.haccpbackend.nettoyagesPostes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CategorieNettoyage {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;


    @NotNull
    @NotEmpty
    @Column(unique = true , nullable = false)
    private String name;


    @OneToMany(mappedBy = "categorieNettoyage", cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnore
    private List<NettoyagesPoste> nettoyagesPostes = new ArrayList<>();


    public CategorieNettoyage() {
    }

    public CategorieNettoyage(Long id, String name, List<NettoyagesPoste> nettoyagesPostes) {
        this.id = id;
        this.name = name;
        this.nettoyagesPostes = nettoyagesPostes;
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

    public List<NettoyagesPoste> getNettoyagesPostes() {
        return nettoyagesPostes;
    }

    public void setNettoyagesPostes(List<NettoyagesPoste> nettoyagesPostes) {
        this.nettoyagesPostes = nettoyagesPostes;
    }
}
