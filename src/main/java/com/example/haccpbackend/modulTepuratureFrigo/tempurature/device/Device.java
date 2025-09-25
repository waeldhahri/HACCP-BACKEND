package com.example.haccpbackend.modulTepuratureFrigo.tempurature.device;


import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;




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
    @JsonBackReference
    private Frigo frigo;



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


    //Constructors

    public Device(Long id, String deviceId, String description, Frigo frigo) {
        this.id = id;
        this.deviceId = deviceId;
        this.description = description;
        this.frigo = frigo;
    }

    public Device() {
    }
}