package com.example.haccpbackend.controleReception.controleReceptionVersion2;

import com.example.haccpbackend.controleReception.Product;
import com.example.haccpbackend.modulFournisseur.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface ReceptionProduitRepository extends JpaRepository<ReceptionProduit,Long> {


    List<ReceptionProduit> findByFournisseursId(Long id);



    Page<ReceptionProduit> findAllByOrderByIdProduitDesc(Pageable pageable);


    List<ReceptionProduit> findByDateReception(LocalDate date);

    List<ReceptionProduit> findByProduitName(String name);

}
