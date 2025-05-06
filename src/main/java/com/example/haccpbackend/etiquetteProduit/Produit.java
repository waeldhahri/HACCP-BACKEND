package com.example.haccpbackend.etiquetteProduit;


import com.example.haccpbackend.modulTepuratureFrigo.CategorieFrigo;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
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


    @Column
    private String photoUrl; // Lien S3 de la photo

    @ManyToOne()
    @JoinColumn(nullable = false, name = "categorie_id")
    private CategorieProduit categorieProduit;

//


    public Produit() {
    }

    public Produit(Long id, String produitname, Double quantite, boolean dlc, LocalDate dateDeStockage,
                   String photoUrl, CategorieProduit categorieProduit) {
        this.id = id;
        this.produitname = produitname;
        this.quantite = quantite;
        this.dlc = dlc;
        this.dateDeStockage = dateDeStockage;
        this.photoUrl = photoUrl;
        this.categorieProduit = categorieProduit;
    }


    //


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduitName() {
        return produitname;
    }

    public void setProduitName(String produitname) {
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public CategorieProduit getCategorieProduit() {
        return categorieProduit;
    }

    public void setCategorieProduit(CategorieProduit categorieProduit) {
        this.categorieProduit = categorieProduit;
    }
}
