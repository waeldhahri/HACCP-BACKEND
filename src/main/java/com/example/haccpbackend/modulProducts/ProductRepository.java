package com.example.haccpbackend.modulProducts;


import com.example.haccpbackend.modulFournisseur.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {

   Optional<Product> findByBarcode(String barcode);

   List<Product> findByDate(LocalDate date);

   Optional<List<Product>> findByProduit (String produit) ;



   Page<Product> findAllByOrderByIdProduitDesc(Pageable pageable);



   Optional<List<Product>> findByQuantiteLessThanEqual(Double quantite);



   Optional<List<Product>> findByFournisseurs_Id(Long fournisseur_id);



   Optional<List<Product>> findByFournisseurs(Fournisseur fournisseur);



   Optional<List<Product>> findByNumeroDuBonDeLivraison (String bondeLivraison);


   Optional<List<Product>> findByNumCamion (String numCamion);









}
