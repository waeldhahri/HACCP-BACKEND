package com.example.haccpbackend.modulFournisseur;


import com.example.haccpbackend.modulProducts.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Fournisseur {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , unique = true)
    private String name;

    @Column(unique = true)
    private String email;


    private String phone ;
    private String address;


    @Lob
    private byte[] contractDetails;


    @OneToMany(mappedBy = "fournisseurs" , cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnoreProperties("fournisseurs")
    private List<Product> productsFournisseur;


    @OneToMany(mappedBy = "fournisseurs" , cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnoreProperties("fournisseurs")
    private List<FournisseurInteraction> fournisseurInteractionList ;


    public Fournisseur(Long id, String name, String email, String phone, String address, byte[] contractDetails,
                       List<Product> productsFournisseur) {
        this.id = id;
        this.name = name;

        this.email = email;
        this.phone = phone;
        this.address = address;
        this.contractDetails = contractDetails;
        this.productsFournisseur = productsFournisseur;
    }


    public Fournisseur() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getContractDetails() {
        return contractDetails;
    }

    public void setContractDetails(byte[] contractDetails) {
        this.contractDetails = contractDetails;
    }

    public List<Product> getProductsFournisseur() {
        return productsFournisseur;
    }

    public void setProductsFournisseur(List<Product> productsFournisseur) {
        this.productsFournisseur = productsFournisseur;
    }
}
