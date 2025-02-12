package com.example.haccpbackend.moduleProducts;


import com.example.haccpbackend.moduleProducts.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends JpaRepository<Product,Long> {


}
