package com.example.haccpbackend.nettoyagesPostes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NettoyagePosteRequest {


    private String nameOfPoste;
    private String name;
    private Long categorieId;

    private String categorie;



    private String note;

    private String validePar; // Nom de la personne qui a validé

    private boolean valide;

    private LocalDate dateOfCreation;

    private LocalTime timeOfCreation;


    public NettoyagePosteRequest() {
    }


    public NettoyagePosteRequest(String nameOfPoste, String name, Long categorieId,
                                 String categorie, String note, String validePar, boolean valide, LocalDate dateOfCreation, LocalTime timeOfCreation) {
        this.nameOfPoste = nameOfPoste;
        this.name = name;
        this.categorieId = categorieId;
        this.categorie = categorie;
        this.note = note;
        this.validePar = validePar;
        this.valide = valide;
        this.dateOfCreation = dateOfCreation;
        this.timeOfCreation = timeOfCreation;
    }




    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getValidePar() {
        return validePar;
    }

    public void setValidePar(String validePar) {
        this.validePar = validePar;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public LocalTime getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(LocalTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }


    public String getNameOfPoste() {
        return nameOfPoste;
    }

    public void setNameOfPoste(String nameOfPoste) {
        this.nameOfPoste = nameOfPoste;
    }


}
