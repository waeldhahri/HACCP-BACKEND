package com.example.haccpbackend.moduleProducts;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "products")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Product {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "produit_id")
    private Long idProduit ;

    @Column(name = "name" , nullable = false )
    private String name;

    @Column(nullable = false )
    private String categorie;

    @Column(nullable = false )
    private String origine;

    @CreatedDate
    @Column( updatable = false)
    private LocalDateTime dateDeCreation ;

    @LastModifiedDate
    @Column(nullable = true )
    private Date datePeremption;

    @Lob
    private byte[] imageOfProduct;


    public Product(Long idProduit, String name, String categorie, String origine, LocalDateTime dateDeCreation, Date datePeremption, byte[] imageOfProduct) {
        this.idProduit = idProduit;
        this.name = name;
        this.categorie = categorie;
        this.origine = origine;
        this.dateDeCreation = dateDeCreation;
        this.datePeremption = datePeremption;
        this.imageOfProduct = imageOfProduct;
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

    public LocalDateTime getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(LocalDateTime dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public Date getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(Date datePeremption) {
        this.datePeremption = datePeremption;
    }

    public byte[] getImageOfProduct() {
        return imageOfProduct;
    }

    public void setImageOfProduct(byte[] imageOfProduct) {
        this.imageOfProduct = imageOfProduct;
    }
}

