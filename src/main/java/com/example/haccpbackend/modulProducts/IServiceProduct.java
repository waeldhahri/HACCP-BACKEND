package com.example.haccpbackend.modulProducts;

import java.util.List;
import java.util.Optional;

public interface IServiceProduct {


    public Optional<Product> getProductByBarcode(String barcode);
    public Product createproduct(Product product);
    public Product findproductById(Long Id);

    public Product updateproduct(Long id,Product newProduct);

    public Product updateProductByBarcode(String barcode , Product newProduct);

    public List<Product> getAllproducts();

    public void deleteproduct(Product product);

}
