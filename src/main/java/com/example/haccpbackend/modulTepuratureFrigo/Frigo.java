package com.example.haccpbackend.modulTepuratureFrigo;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Frigo {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name ;

    @ManyToOne()
    @JoinColumn(nullable = false, name = "categorie_id")
    private Categorie categorie;


    @OneToMany(mappedBy = "frigo", cascade = CascadeType.ALL)
    private List<TemperatureFrigo> temperatures;

    @Lob
    private byte[] imageOfFrigo;


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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
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


    public Frigo(Long id, String name, Categorie categorie, List<TemperatureFrigo> temperatures, byte[] imageOfFrigo) {
        this.id = id;
        this.name = name;
        this.categorie = categorie;
        this.temperatures = temperatures;
        this.imageOfFrigo = imageOfFrigo;
    }

    public Frigo() {
    }
}
