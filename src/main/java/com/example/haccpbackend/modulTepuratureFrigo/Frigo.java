package com.example.haccpbackend.modulTepuratureFrigo;


import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningFrigo.PlanningFrigo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "categorie_id"})
)
public class Frigo {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name ;

    @ManyToOne()
    @JoinColumn(nullable = false, name = "categorie_id")
    private CategorieFrigo categorieFrigo;

    @OneToMany(mappedBy = "frigo", cascade = CascadeType.ALL , orphanRemoval = true )
    @JsonManagedReference
    private List<PlanningFrigo> planningFrigos;


    @OneToMany(mappedBy = "frigo", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<TemperatureFrigo> temperatures;

    @JsonIgnore
    @Lob
    private byte[] imageOfFrigo;

    private String imageUrl ;

    public CategorieFrigo getCategorieFrigo() {
        return categorieFrigo;
    }

    public void setCategorieFrigo(CategorieFrigo categorieFrigo) {
        this.categorieFrigo = categorieFrigo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public CategorieFrigo getCategorie() {
        return categorieFrigo;
    }

    public void setCategorie(CategorieFrigo categorieFrigo) {
        this.categorieFrigo = categorieFrigo;
    }

    public List<TemperatureFrigo> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<TemperatureFrigo> temperatures) {
        this.temperatures = temperatures;
    }

    public byte[] getImageOfFrigo() {
        return imageOfFrigo;
    }

    public void setImageOfFrigo(byte[] imageOfFrigo) {
        this.imageOfFrigo = imageOfFrigo;
    }

    public List<PlanningFrigo> getPlanningFrigos() {
        return planningFrigos;
    }

    public void setPlanningFrigos(List<PlanningFrigo> planningFrigos) {
        this.planningFrigos = planningFrigos;
    }


    public Frigo(Long id, String name, CategorieFrigo categorieFrigo, List<PlanningFrigo> planningFrigos,
                 List<TemperatureFrigo> temperatures, byte[] imageOfFrigo, String imageUrl) {
        this.id = id;
        this.name = name;
        this.categorieFrigo = categorieFrigo;
        this.planningFrigos = planningFrigos;
        this.temperatures = temperatures;
        this.imageOfFrigo = imageOfFrigo;
        this.imageUrl = imageUrl;
    }

    public Frigo() {
    }
}
