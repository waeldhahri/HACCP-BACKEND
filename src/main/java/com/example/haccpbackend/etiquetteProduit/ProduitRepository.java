package com.example.haccpbackend.etiquetteProduit;

import com.example.haccpbackend.controleReception.controleReceptionVersion2.ReceptionProduit;
import com.example.haccpbackend.nettoyagesPostes.NettoyagesPoste;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit,Long> {




    List<Produit> findAllByDateDeStockage(LocalDate dateDeStockage);


    List<Produit> findAllByDateDeOuverture(LocalDate dateOuverture);


    List<Produit> findAllByDateDeFabrication(LocalDate dateDeFabrication);


    public List<Produit> findByCategorieProduit_NameIgnoreCase(String name);


    List<Produit> findByProduitnameIgnoreCase(String nameProduit);



    Page<Produit> findAllByOrderByIdDesc(Pageable pageable);





}
