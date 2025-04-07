package com.example.haccpbackend.modulTepuratureFrigo;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TemperatureFrigo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double temperature;

    private LocalDateTime dateMesure;

    @ManyToOne
    @JoinColumn(name = "frigo_id")
    private Frigo frigo;


    public TemperatureFrigo(Long id, double temperature, LocalDateTime dateMesure, Frigo frigo) {
        this.id = id;
        this.temperature = temperature;
        this.dateMesure = dateMesure;
        this.frigo = frigo;
    }


    public TemperatureFrigo() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public LocalDateTime getDateMesure() {
        return dateMesure;
    }

    public void setDateMesure(LocalDateTime dateMesure) {
        this.dateMesure = dateMesure;
    }

    public Frigo getFrigo() {
        return frigo;
    }

    public void setFrigo(Frigo frigo) {
        this.frigo = frigo;
    }
}
