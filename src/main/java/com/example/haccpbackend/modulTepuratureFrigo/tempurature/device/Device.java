package com.example.haccpbackend.modulTepuratureFrigo.tempurature.device;


import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import com.example.haccpbackend.modulTepuratureFrigo.tempurature.TemperatureFrigoMqTT;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String deviceId;   // ex: "TX1A2B3C4D"

    private String description; // optionnel (ex: capteur entrée, capteur haut, etc.)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frigo_id")
    @JsonManagedReference("frigo-device")
    private Frigo frigo;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL , orphanRemoval = true )
    @JsonManagedReference("deviceRef")
    @JsonIgnore
    private List<TemperatureFrigoMqTT> temperatureFrigoMqTTS;


/*

    // Relation avec Tempurature
    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL , orphanRemoval = true )
    @JsonManagedReference
    private List<TemperatureFrigoMqTT> temperatureFrigoMqTTS;

*/

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Frigo getFrigo() {
        return frigo;
    }

    public void setFrigo(Frigo frigo) {
        this.frigo = frigo;
    }

    public List<TemperatureFrigoMqTT> getTemperatureFrigoMqTTS() {
        return temperatureFrigoMqTTS;
    }

    public void setTemperatureFrigoMqTTS(List<TemperatureFrigoMqTT> temperatureFrigoMqTTS) {
        this.temperatureFrigoMqTTS = temperatureFrigoMqTTS;
    }


    //Constructors


    public Device(Long id, String deviceId, String description
            , Frigo frigo, List<TemperatureFrigoMqTT> temperatureFrigoMqTTS) {
        this.id = id;
        this.deviceId = deviceId;
        this.description = description;
        this.frigo = frigo;
        this.temperatureFrigoMqTTS = temperatureFrigoMqTTS;
    }

    public Device() {
    }



}