package com.example.haccpbackend.modulProducts;

import java.time.LocalDate;

public class ProductDTO {

    public ProductDTO() {
    }

    private String name;
    private String categorie;
    private String origine;
    private String barcode;


    private LocalDate datePeremption;

    private String imageUrl; // URL de l'image



    public ProductDTO(String name, String categorie, String origine, String barcode, LocalDate datePeremption , String imageUrl) {
        this.name = name;
        this.categorie = categorie;
        this.origine = origine;
        this.barcode = barcode;
        this.datePeremption = datePeremption;
        this.imageUrl=imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public LocalDate getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(LocalDate datePeremption) {
        this.datePeremption = datePeremption;
    }
}
