package com.example.haccpbackend.modulProducts;


import org.springframework.data.jpa.repository.JpaRepository;
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


}
