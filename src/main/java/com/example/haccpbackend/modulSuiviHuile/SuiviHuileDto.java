package com.example.haccpbackend.modulSuiviHuile;

public class SuiviHuileDto {






    public SuiviHuileDto() {
    }

    public SuiviHuileDto(String nameOfFriteuse, String note) {
        this.nameOfFriteuse = nameOfFriteuse;
        this.note = note;
    }

    private String nameOfFriteuse ;


    private String note;


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
}
