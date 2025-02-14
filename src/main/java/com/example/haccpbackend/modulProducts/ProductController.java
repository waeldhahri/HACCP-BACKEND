package com.example.haccpbackend.modulProducts;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {



    @Autowired
    private  IServiceProduct iServiceProduct;



    public ProductController( IServiceProduct iServiceProduct) {

        this.iServiceProduct = iServiceProduct;

    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Product> getAllProducts(){

        return iServiceProduct.getAllproducts();

    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product){

      return ResponseEntity.status(HttpStatus.CREATED).body(iServiceProduct.createproduct(product));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> findProductById(@PathVariable Long id){

        return ResponseEntity.ok(iServiceProduct.findproductById(id));
    }



    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){

        //   tokenRepository.clearUserReferences(userId);

        //sessionRepository.clearEmployeeReferences(userId);

        iServiceProduct.deleteproduct(iServiceProduct.findproductById(id));

        return ResponseEntity.noContent().build();
    }




    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id , @Valid @RequestBody Product product ){
        return ResponseEntity.status(HttpStatus.CREATED).body(iServiceProduct.updateproduct(id ,product));
    }

}
