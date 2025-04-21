package com.example.haccpbackend.etiquetteProduit;


import com.example.haccpbackend.modulTepuratureFrigo.CategorieFrigo;
import jakarta.persistence.*;

@Entity
public class Produit {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "produit" , nullable = false , insertable = false)
    private String produit;



    @Column(name = "quantité" , nullable = false)
    private Double quantite;

    @Column(nullable = false)
    private boolean dlc;


    @Column
    private String photoUrl; // Lien S3 de la photo

    @ManyToOne()
    @JoinColumn(nullable = false, name = "categorie_id")
    private CategorieProduit categorieProduit;

//


    public Produit() {
    }


    public Produit(Long id, String produit, Double quantite, boolean dlc, String photoUrl, CategorieProduit categorieProduit) {
        this.id = id;
        this.produit = produit;
        this.quantite = quantite;
        this.dlc = dlc;
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

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
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

    public CategorieProduit getCategorieProduit() {
        return categorieProduit;
    }

    public void setCategorieProduit(CategorieProduit categorieProduit) {
        this.categorieProduit = categorieProduit;
    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
