package com.example.haccpbackend.modulProducts;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IServiceProduct {


    public Optional<Product> getProductByBarcode(String barcode);
    public Product createproduct(Product product);
    public Product findproductById(Long Id);

    public List<Product> getProductByDate(LocalDate dateDeCreation);

    public Optional<List<Product>> getProductByName(String name);

    public Product updateproduct(Long id,Product newProduct);

    public Product updateProductByBarcode(String barcode , Product newProduct);

    public List<Product> getAllproducts();

    public void deleteproduct(Product product);

}
