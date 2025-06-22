package com.example.haccpbackend.controleReception;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IServiceProduct {


    public Optional<Product> getProductByBarcode(String barcode);
    public Product createproduct(Product product);
    public Product findproductById(Long Id);

    public List<Product> getProductByDate(LocalDate dateDeCreation);

    public List<Product> getProductByProduit(String produit);

    public List<Product> getProductByQuantite(Double quantite);

    public List<Product> getProductByFournisseurId(Long fournisseurId);


    public Product updateproduct(Long id, ProductDTO productDTO, MultipartFile file);

    public Product updateProductByBarcode(String barcode , Product newProduct );

    public List<Product> getAllproducts();

    public void deleteproduct(Product product);

}
