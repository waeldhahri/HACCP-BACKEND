package com.example.haccpbackend.modulTepuratureFrigo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CategorieFrigo {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;


    @NotNull
    @NotEmpty
    @Column(unique = true , nullable = false)
    private String name;

    @OneToMany(mappedBy = "categorieFrigo", cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnore
    private List<Frigo> frigos = new ArrayList<>();


    public CategorieFrigo() {
    }


    public CategorieFrigo(Long id, String name, List<Frigo> frigos) {
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
