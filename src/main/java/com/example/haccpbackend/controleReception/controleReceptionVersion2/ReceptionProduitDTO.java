package com.example.haccpbackend.controleReception.controleReceptionVersion2;

public class ReceptionProduitDTO {


    private String note;
    private Long fournisseurId;
    private String produitName;


    //Contructors


    public ReceptionProduitDTO(String note, Long fournisseurId, String produitName) {
        this.note = note;
        this.fournisseurId = fournisseurId;
        this.produitName = produitName;
    }

    public ReceptionProduitDTO() {
    }


    //Getter and Setters


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public String getProduitName() {
        return produitName;
    }

    public void setProduitName(String produitName) {
        this.produitName = produitName;
    }
}
