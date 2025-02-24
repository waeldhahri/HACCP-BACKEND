package com.example.haccpbackend.modulProducts;

import com.example.haccpbackend.modulFournisseur.Fournisseur;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "products")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Product {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produit_id")
    private Long idProduit ;

    @Column(name = "name" , nullable = false )
    private String name;

    @Column(nullable = false )
    private String categorie;

    @Column(nullable = false )
    private String origine;

    @CreatedDate
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column( updatable = false)
    private LocalDate dateDeCreation ;

    @LastModifiedDate
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = true )
    private LocalDate datePeremption;

    @Lob
    private byte[] imageOfProduct;


    private String barcode;


    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseurs;


    public Product(Long idProduit, String name, String categorie, String origine, LocalDate dateDeCreation, LocalDate datePeremption
            , String barcode, byte[] imageOfProduct , Fournisseur fournisseurs) {
        this.idProduit = idProduit;
        this.name = name;
        this.categorie = categorie;
        this.origine = origine;
        this.dateDeCreation = dateDeCreation;
        this.datePeremption = datePeremption;
        this.imageOfProduct = imageOfProduct;
        this.barcode = barcode ;
        this.fournisseurs=fournisseurs;
    }


    public Product() {
    }


    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
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

    public LocalDate getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(LocalDate dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public LocalDate getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(LocalDate datePeremption) {
        this.datePeremption = datePeremption;
    }

    public byte[] getImageOfProduct() {
        return imageOfProduct;
    }

    public void setImageOfProduct(byte[] imageOfProduct) {
        this.imageOfProduct = imageOfProduct;
    }


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }


    public Fournisseur getFournisseur() {
        return fournisseurs;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseurs = fournisseur;
    }
}

