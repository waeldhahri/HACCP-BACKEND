package com.example.haccpbackend.modulProducts;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.hibernate.validator.internal.util.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
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

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ServiceProduct serviceProduct;

    @Autowired
    private ObjectMapper objectMapper;



    public ProductController( IServiceProduct iServiceProduct , ObjectMapper objectMapper) {

        this.iServiceProduct = iServiceProduct;
        this.objectMapper = objectMapper;

    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Product> getAllProducts(){

        return iServiceProduct.getAllproducts();

    }



    @GetMapping("/page")
    public Page<Product> getAllProduct(Pageable pageable){

        return productRepository.findAllByOrderByIdProduitDesc(pageable);


       // return productRepository.findAll();

        //return iServiceProduct.getAllproducts();
    }













/*

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product){

      return ResponseEntity.status(HttpStatus.CREATED).body(iServiceProduct.createproduct(product));
    }*/






    @PostMapping(value = "", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> createProduct(/*@Valid @RequestBody Product product*/
            @RequestPart("product") String productJson ,
             @RequestPart(value = "file", required = false) MultipartFile file){


        try {

            @Valid
            // Convertir JSON en objet ProductDTO
            ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);

            // Créer une nouvelle entité Product
            @Valid
            Product product = new Product();

           product.setProduit(productDTO.getProduit());
           product.setDate(productDTO.getDate());
           product.setNumeroDuBonDeLivraison(productDTO.getNumeroDeBonLivraison());
           product.setNumCamion(productDTO.getNumeroTCamion());
           product.setPropreteCamion(productDTO.getNumeroPropreteCamion());
           product.setHeureDeLivraison(productDTO.getHeureDeLivraison());
           product.settProduit(productDTO.gettDeProduit());
           product.setIntegrite(productDTO.isIntegrite());
           product.setDlcORddm(productDTO.getDlc());
           product.setNumeroDeLot(productDTO.getNumeroDeLot());
           product.setQuantite(productDTO.getQuantite());
           product.setHeureDeStockage(productDTO.getHeureDeStockage());





            product.setBarcode(productDTO.getBarcode());


            // Gérer l'image
            if (file != null) {

                product.setImageOfProduct(file.getBytes());

                // Générer l'URL complète de l'image
                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/products/")
                        .path(product.getIdProduit().toString())
                        .path("/image")
                        .toUriString();

                product.setImageUrl(imageUrl);


            } else

                    { product.setImageOfProduct(null);
                        product.setImageUrl(null);
                        String imageUrl = null;
                        product.setImageUrl(imageUrl);
                    }


            // Sauvegarder dans la base de données
            Product savedProduct = iServiceProduct.createproduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);


           // return ResponseEntity.status(HttpStatus.CREATED).body(iServiceProduct.createproduct(product));

        }
        catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }


    }





    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {

        Optional<Product> productOptional =productRepository.findById(id);
        Optional<Product> productOptional2 =productRepository.findById(id);

       // Optional<Product> productOptional = iServiceProduct.findproductById(id);
        if (productOptional.isEmpty() || productOptional.get().getImageOfProduct() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(productOptional.get().getImageOfProduct());
    }











    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> findProductById(@PathVariable Long id){

        Product product = iServiceProduct.findproductById(id);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        // Générer l'URL complète de l'image
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/products/")
                .path(product.getIdProduit().toString())
                .path("/image")
                .toUriString();


        product.setImageUrl(imageUrl);

        return ResponseEntity.ok(product);



        // return ResponseEntity.ok(iServiceProduct.findproductById(id));
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

    @GetMapping("/quantite/{quantite}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Product>> findProductByQuantite(@PathVariable Double quantite){

        return iServiceProduct.getProductByQuantite(quantite).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping("/by-fournisseur/{fournisseurId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Product>> getProductsByFournisseur(@PathVariable Long fournisseurId){

        return iServiceProduct.getProductByFournisseurId(fournisseurId)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }



    @GetMapping("/produit/{produit}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Product>> findProductByProduit(@PathVariable String produit){


        return iServiceProduct.getProductByProduit(produit).map(ResponseEntity::ok)
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




    @PutMapping(value = "/update/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id
            , @RequestPart("product") String productJson ,  @RequestPart(value = "file", required = false) MultipartFile file)

             {

        /* @Valid @RequestBody Product product ){*/
        //return ResponseEntity.status(HttpStatus.CREATED).body(iServiceProduct.updateproduct(id ,product));


                 try {


                     System.out.println(productJson);



                     // Convertir le JSON en objet ProductDTO
                     ObjectMapper objectMapper = new ObjectMapper();
                     ProductDTO productDTO = objectMapper.readValue(productJson, ProductDTO.class);

                     // Appeler le service pour mettre à jour le produit
                     Product updatedProduct = iServiceProduct.updateproduct(id, productDTO, file);

                     return ResponseEntity.ok(updatedProduct);

                 } catch (Exception e) {
                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                 }



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
