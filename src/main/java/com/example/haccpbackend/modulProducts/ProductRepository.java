package com.example.haccpbackend.modulProducts;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {

   Optional<Product> findByBarcode(String barcode);

}
