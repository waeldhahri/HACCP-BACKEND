package com.example.haccpbackend.modulPlanning;


import com.example.haccpbackend.etiquetteProduit.Produit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PlanningCategorie {





    public PlanningCategorie() {
    }

    public PlanningCategorie(Long id, String name, List<Planning> plannings) {
        this.id = id;
        this.name = name;
        this.plannings = plannings;
    }





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;


    @NotNull
    @NotEmpty
    @Column(unique = true , nullable = false)
    private String name;



    @OneToMany(mappedBy = "planningCategorie", cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnore
    private List<Planning> plannings = new ArrayList<>();





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

    public List<Planning> getPlannings() {
        return plannings;
    }

    public void setPlannings(List<Planning> plannings) {
        this.plannings = plannings;
    }
}
