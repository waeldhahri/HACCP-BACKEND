package com.example.haccpbackend.modulProducts;

import java.util.List;

public interface IServiceProduct {



    public Product createproduct(Product product);
    public Product findproductById(Long Id);

    public Product updateproduct(Long id,Product newProduct);

    public List<Product> getAllproducts();

    public void deleteproduct(Product product);

}
