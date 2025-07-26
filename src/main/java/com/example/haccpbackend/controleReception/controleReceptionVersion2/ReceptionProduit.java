package com.example.haccpbackend.controleReception.controleReceptionVersion2;


import com.example.haccpbackend.modulFournisseur.Fournisseur;
import com.example.haccpbackend.modulUsers.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ReceptionProduit {





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produit_id")
    private Long idProduit ;



    private String produitName;


    private String note;



    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseurs;



    @JsonIgnore
    @Lob
    private byte[] imageOfProduct;


    private String imageUrl ;



    @CreatedDate
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateReception;






//Getter and Setters


    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Fournisseur getFournisseurs() {
        return fournisseurs;
    }

    public void setFournisseurs(Fournisseur fournisseurs) {
        this.fournisseurs = fournisseurs;
    }



    public byte[] getImageOfProduct() {
        return imageOfProduct;
    }

    public void setImageOfProduct(byte[] imageOfProduct) {
        this.imageOfProduct = imageOfProduct;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProduitName() {
        return produitName;
    }

    public void setProduitName(String produitName) {
        this.produitName = produitName;
    }

    public LocalDate getDateReception() {
        return dateReception;
    }

    public void setDateReception(LocalDate dateReception) {
        this.dateReception = dateReception;
    }

    // Constructors


    public ReceptionProduit(Long idProduit, String produitName, String note, Fournisseur fournisseurs
            , byte[] imageOfProduct, String imageUrl, LocalDate dateReception) {
        this.idProduit = idProduit;
        this.produitName = produitName;
        this.note = note;
        this.fournisseurs = fournisseurs;
        this.imageOfProduct = imageOfProduct;
        this.imageUrl = imageUrl;
        this.dateReception = dateReception;
    }

    public ReceptionProduit() {
    }


}
