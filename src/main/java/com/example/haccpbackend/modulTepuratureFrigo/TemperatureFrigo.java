package com.example.haccpbackend.modulTepuratureFrigo;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class TemperatureFrigo {





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double temperature;

    private LocalDateTime dateMesure;

    @ManyToOne
    @JoinColumn(name = "frigo_id")
    private Frigo frigo;

    @CreatedDate
    private LocalDateTime dateTime;


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
