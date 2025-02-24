package com.example.haccpbackend.modulProducts;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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


    @GetMapping("/date/{date}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Product>> findProductBydate(@PathVariable String date){


        try {
            // Conversion de String → LocalDate avec le format dd-MM-yyyy
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(date, formatter);

            List<Product> products = iServiceProduct.getProductByDate(localDate);
            return products.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(products);

        } catch (Exception e) {
            //return ResponseEntity.badRequest().body(null); // Erreur si la date est mal formatée
            return ResponseEntity.notFound().build();
        }









       //return iServiceProduct.getProductByDate(date).map(ResponseEntity::ok)
            //.orElse(ResponseEntity.notFound().build());

    }


    @GetMapping("/name/{name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Product>> findProductByname(@PathVariable String name){


        return iServiceProduct.getProductByName(name).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

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



    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<Product> getProductByBarcode(@PathVariable String barcode) {
        return iServiceProduct.getProductByBarcode(barcode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
              //  .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
    }


    // Mettre à jour un produit via un code-barres
    @PutMapping("/barcode/{barcode}")
    public ResponseEntity<Product> updateProductByBarcode(@PathVariable String barcode,
                                                          @RequestBody Product newproduct) {


        try {
            Product product = iServiceProduct.updateProductByBarcode(barcode, newproduct);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
