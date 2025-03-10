package com.example.haccpbackend.modulProducts;

import java.time.LocalDate;
import java.time.LocalTime;

public class ProductDTO {

    public ProductDTO() {
    }



    private LocalDate date ;


    private String produit;

    private String numeroDeBonLivraison;


    private String numeroTCamion;

    private String numeroPropreteCamion;


    private LocalTime heureDeLivraison;

    private String tDeProduit;

    private boolean integrite;


    private String dlc ;

    private String numeroDeLot;

    private Double quantite;

    private LocalTime heureDeStockage;

    private String signature;






    private String barcode;

    private String imageUrl; // URL de l'image





    public ProductDTO(LocalDate date, String produit, String numeroDeBonLivraison, String numeroTCamion,
                      String numeroPropreteCamion, LocalTime heureDeLivraison, String tDeProduit, boolean integrite,
                      String dlc, String numeroDeLot, Double quantite, LocalTime heureDeStockage, String signature, String barcode, String imageUrl) {
        this.date = date;
        this.produit = produit;
        this.numeroDeBonLivraison = numeroDeBonLivraison;
        this.numeroTCamion = numeroTCamion;
        this.numeroPropreteCamion = numeroPropreteCamion;
        this.heureDeLivraison = heureDeLivraison;
        this.tDeProduit = tDeProduit;
        this.integrite = integrite;
        this.dlc = dlc;
        this.numeroDeLot = numeroDeLot;
        this.quantite = quantite;
        this.heureDeStockage = heureDeStockage;
        this.signature = signature;
        this.barcode = barcode;
        this.imageUrl = imageUrl;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getNumeroDeBonLivraison() {
        return numeroDeBonLivraison;
    }

    public void setNumeroDeBonLivraison(String numeroDeBonLivraison) {
        this.numeroDeBonLivraison = numeroDeBonLivraison;
    }

    public String getNumeroTCamion() {
        return numeroTCamion;
    }

    public void setNumeroTCamion(String numeroTCamion) {
        this.numeroTCamion = numeroTCamion;
    }

    public String getNumeroPropreteCamion() {
        return numeroPropreteCamion;
    }

    public void setNumeroPropreteCamion(String numeroPropreteCamion) {
        this.numeroPropreteCamion = numeroPropreteCamion;
    }

    public LocalTime getHeureDeLivraison() {
        return heureDeLivraison;
    }

    public void setHeureDeLivraison(LocalTime heureDeLivraison) {
        this.heureDeLivraison = heureDeLivraison;
    }

    public String gettDeProduit() {
        return tDeProduit;
    }

    public void settDeProduit(String tDeProduit) {
        this.tDeProduit = tDeProduit;
    }

    public boolean isIntegrite() {
        return integrite;
    }

    public void setIntegrite(boolean integrite) {
        this.integrite = integrite;
    }

    public String getDlc() {
        return dlc;
    }

    public void setDlc(String dlc) {
        this.dlc = dlc;
    }

    public String getNumeroDeLot() {
        return numeroDeLot;
    }

    public void setNumeroDeLot(String numeroDeLot) {
        this.numeroDeLot = numeroDeLot;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public LocalTime getHeureDeStockage() {
        return heureDeStockage;
    }

    public void setHeureDeStockage(LocalTime heureDeStockage) {
        this.heureDeStockage = heureDeStockage;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
