package com.example.haccpbackend.modulTepuratureFrigo.tempurature;

import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import com.example.haccpbackend.organisation.Organisation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TemperatureFrigoMqTT {





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double temperature;
    private Integer batteryLevel;
    private LocalDateTime datetime;

    @ManyToOne
    @JoinColumn(name = "frigo_id")
    private Frigo frigo;


    @ManyToOne
    @JoinColumn(name = "organisation_id")
    @JsonBackReference
    private Organisation organisation;


    //constructors


    public TemperatureFrigoMqTT(Long id, Double temperature,
                                Integer batteryLevel, LocalDateTime datetime,
                                            Frigo frigo, Organisation organisation) {
        this.id = id;
        this.temperature = temperature;
        this.batteryLevel = batteryLevel;
        this.datetime = datetime;
        this.frigo = frigo;
        this.organisation = organisation;
    }

    public TemperatureFrigoMqTT() {
    }

    //Getter and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Frigo getFrigo() {
        return frigo;
    }

    public void setFrigo(Frigo frigo) {
        this.frigo = frigo;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
}
