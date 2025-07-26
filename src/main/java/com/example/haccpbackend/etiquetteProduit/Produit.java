package com.example.haccpbackend.etiquetteProduit;


import com.example.haccpbackend.modulTepuratureFrigo.CategorieFrigo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Produit {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String produitname;



    @Column
    private Double quantite;

    @Column(nullable = false)
    private boolean dlc;





    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDeStockage;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDeOuverture;





    @Column
    private String photoUrl; // Lien S3 de la photo

    @Column
    private String photoDeOuvertureUrl;



    @ManyToOne()
    @JoinColumn(nullable = false, name = "categorie_id")
    private CategorieProduit categorieProduit;

//Getter and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduitname() {
        return produitname;
    }

    public void setProduitname(String produitname) {
        this.produitname = produitname;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public boolean isDlc() {
        return dlc;
    }

    public void setDlc(boolean dlc) {
        this.dlc = dlc;
    }

    public LocalDate getDateDeStockage() {
        return dateDeStockage;
    }

    public void setDateDeStockage(LocalDate dateDeStockage) {
        this.dateDeStockage = dateDeStockage;
    }

    public LocalDate getDateDeOuverture() {
        return dateDeOuverture;
    }

    public void setDateDeOuverture(LocalDate dateDeOuverture) {
        this.dateDeOuverture = dateDeOuverture;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoDeOuvertureUrl() {
        return photoDeOuvertureUrl;
    }

    public void setPhotoDeOuvertureUrl(String photoDeOuvertureUrl) {
        this.photoDeOuvertureUrl = photoDeOuvertureUrl;
    }

    public CategorieProduit getCategorieProduit() {
        return categorieProduit;
    }

    public void setCategorieProduit(CategorieProduit categorieProduit) {
        this.categorieProduit = categorieProduit;
    }

    //Constructors


    public Produit(Long id, String produitname, Double quantite, boolean dlc, LocalDate dateDeStockage, LocalDate dateDeOuverture,
                   String photoUrl, String photoDeOuvertureUrl, CategorieProduit categorieProduit) {
        this.id = id;
        this.produitname = produitname;
        this.quantite = quantite;
        this.dlc = dlc;
        this.dateDeStockage = dateDeStockage;
        this.dateDeOuverture = dateDeOuverture;
        this.photoUrl = photoUrl;
        this.photoDeOuvertureUrl = photoDeOuvertureUrl;
        this.categorieProduit = categorieProduit;
    }

    public Produit() {
    }
}
