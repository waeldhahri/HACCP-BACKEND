package com.example.haccpbackend.modulProducts;

import com.example.haccpbackend.modulFournisseur.Fournisseur;
import com.example.haccpbackend.modulUsers.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Column(name = "produit" , nullable = false )
    private String produit;



    @Column(name = "date" , updatable = false , nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date ;


    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseurs;


    @Column(name = "numero De Bon de Livraison" ,  nullable = false)
    private String numeroDuBonDeLivraison ;



    @Column(name = "numero Camion" , nullable = false)
    private String numCamion ;



    @Column(name = "propreté Camion" , nullable = false)
    private String propreteCamion;



    @Column(name = "heure de Livraison " , nullable = false)
    private LocalTime heureDeLivraison;


    @Column(name = "t° produit " , nullable = false)
    private String tProduit;



    @Column(name = "integrite Emballage" , nullable = false)
    private boolean integrite;


    @Column(name = "DLC or DDM" , nullable = false)
    private String dlcORddm ;


    @Column(name = "N ° de lot " , nullable = false)
    private String numeroDeLot;


    @Column(name = "quantité" , nullable = false)
    private Double quantite;


    @Column(name = "Heure de Stockage" , nullable = false)
    private LocalTime heureDeStockage;





    @ManyToOne
    @JoinColumn(name = "signature")
    private User users;



    @JsonIgnore
    @Lob
    private byte[] imageOfProduct;


    private String imageUrl ;



    private String barcode;


    public Product(Long idProduit, String produit, LocalDate date, Fournisseur fournisseurs, String numeroDuBonDeLivraison, String numCamion,
                   String propreteCamion, LocalTime heureDeLivraison, String tProduit, boolean integrite, String dlcORddm,
                   String numeroDeLot, Double quantite, LocalTime heureDeStockage, User users, byte[] imageOfProduct, String imageUrl, String barcode) {
        this.idProduit = idProduit;
        this.produit = produit;
        this.date = date;
        this.fournisseurs = fournisseurs;
        this.numeroDuBonDeLivraison = numeroDuBonDeLivraison;
        this.numCamion = numCamion;
        this.propreteCamion = propreteCamion;
        this.heureDeLivraison = heureDeLivraison;
        this.tProduit = tProduit;
        this.integrite = integrite;
        this.dlcORddm = dlcORddm;
        this.numeroDeLot = numeroDeLot;
        this.quantite = quantite;
        this.heureDeStockage = heureDeStockage;
        this.users = users;
        this.imageOfProduct = imageOfProduct;
        this.imageUrl = imageUrl;
        this.barcode = barcode;
    }

    public Product() {
    }

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Fournisseur getFournisseurs() {
        return fournisseurs;
    }

    public void setFournisseurs(Fournisseur fournisseurs) {
        this.fournisseurs = fournisseurs;
    }

    public String getNumeroDuBonDeLivraison() {
        return numeroDuBonDeLivraison;
    }

    public void setNumeroDuBonDeLivraison(String numeroDuBonDeLivraison) {
        this.numeroDuBonDeLivraison = numeroDuBonDeLivraison;
    }

    public String getNumCamion() {
        return numCamion;
    }

    public void setNumCamion(String numCamion) {
        this.numCamion = numCamion;
    }

    public String getPropreteCamion() {
        return propreteCamion;
    }

    public void setPropreteCamion(String propreteCamion) {
        this.propreteCamion = propreteCamion;
    }

    public LocalTime getHeureDeLivraison() {
        return heureDeLivraison;
    }

    public void setHeureDeLivraison(LocalTime heureDeLivraison) {
        this.heureDeLivraison = heureDeLivraison;
    }

    public String gettProduit() {
        return tProduit;
    }

    public void settProduit(String tProduit) {
        this.tProduit = tProduit;
    }

    public boolean isIntegrite() {
        return integrite;
    }

    public void setIntegrite(boolean integrite) {
        this.integrite = integrite;
    }

    public String getDlcORddm() {
        return dlcORddm;
    }

    public void setDlcORddm(String dlcORddm) {
        this.dlcORddm = dlcORddm;
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

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}

