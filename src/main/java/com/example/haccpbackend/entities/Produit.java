package com.example.haccpbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Produit {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "produit-id")
    private Long Id_produit ;

    @Column(name = "name of product" , nullable = false )
    private String name;

    @Column(nullable = false)
    private String categorie;

    @Column(nullable = false)
    private String origine;

    @CreatedDate
    @Column(nullable = false , updatable = false)
    private LocalDateTime dateDeCreation ;

    @Column(nullable = false)
    private Date datePeremption;

    @Lob
    private byte[] imageOfProduct;


}
