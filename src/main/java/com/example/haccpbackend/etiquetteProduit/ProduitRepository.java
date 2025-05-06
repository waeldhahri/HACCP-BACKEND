package com.example.haccpbackend.etiquetteProduit;

import com.example.haccpbackend.nettoyagesPostes.NettoyagesPoste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit,Long> {




    List<Produit> findAllByDateDeStockage(LocalDate dateDeStockage);


    public List<Produit> findByCategorieProduit_NameIgnoreCase(String name);






}
