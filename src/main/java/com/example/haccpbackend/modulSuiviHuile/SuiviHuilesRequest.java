package com.example.haccpbackend.modulSuiviHuile;

import java.time.LocalDate;
import java.time.LocalTime;

public class SuiviHuilesRequest {


    public SuiviHuilesRequest() {
    }

    public SuiviHuilesRequest(String nameOfFriteuse, String note, String validePar, boolean valide, LocalDate dateOfCreation, LocalTime timeOfCreation) {
        this.nameOfFriteuse = nameOfFriteuse;
        this.note = note;
        this.validePar = validePar;
        this.valide = valide;
        this.dateOfCreation = dateOfCreation;
        this.timeOfCreation = timeOfCreation;
    }

    private String nameOfFriteuse;

    private String note;

    private String validePar; // Nom de la personne qui a validé

    private boolean valide;

    private LocalDate dateOfCreation;

    private LocalTime timeOfCreation;


    public String getNameOfFriteuse() {
        return nameOfFriteuse;
    }

    public void setNameOfFriteuse(String nameOfFriteuse) {
        this.nameOfFriteuse = nameOfFriteuse;
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
}
