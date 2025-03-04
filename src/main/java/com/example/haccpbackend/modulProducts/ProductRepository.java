package com.example.haccpbackend.modulProducts;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {

   Optional<Product> findByBarcode(String barcode);

   List<Product> findByDateDeCreation(LocalDate dateDeCreation);

   Optional<List<Product>> findByName (String name) ;



   Page<Product> findAllByOrderByIdProduitDesc(Pageable pageable);


   Optional<Product> findByIdProduit(Long id);


   //Page<Product> findProductsByCategorie(Pageable pageable);



   @Query("SELECT DISTINCT p.categorie FROM Product p")
   List<String> findDistinctCategories();




   @Query("SELECT  p.name FROM Product p WHERE LOWER(p.categorie) IN :categories")
   List<String> findProductNamesByCategories(@Param("categories") List<String> categories);




}
