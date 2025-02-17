package com.example.haccpbackend.modulFournisseur;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class FournisseurInteraction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String interactionType;
    private String details;
    private LocalDateTime timestamp;


    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseurs;


    public FournisseurInteraction() {
    }


    public FournisseurInteraction(Long id, String interactionType, String details, LocalDateTime timestamp, Fournisseur fournisseurs) {
        this.id = id;
        this.interactionType = interactionType;
        this.details = details;
        this.timestamp = timestamp;
        this.fournisseurs = fournisseurs;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(String interactionType) {
        this.interactionType = interactionType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Fournisseur getFournisseurs() {
        return fournisseurs;
    }

    public void setFournisseurs(Fournisseur fournisseurs) {
        this.fournisseurs = fournisseurs;
    }
}
