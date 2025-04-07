package com.example.haccpbackend.modulTepuratureFrigo;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Categorie {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String name;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)
    private List<Frigo> frigos = new ArrayList<>();


    public Categorie() {
    }


    public Categorie(Long id, String name, List<Frigo> frigos) {
        this.id = id;
        this.name = name;
        this.frigos = frigos;
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

    public List<Frigo> getFrigos() {
        return frigos;
    }

    public void setFrigos(List<Frigo> frigos) {
        this.frigos = frigos;
    }
}
