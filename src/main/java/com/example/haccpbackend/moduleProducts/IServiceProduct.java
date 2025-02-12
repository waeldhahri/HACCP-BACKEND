package com.example.haccpbackend.moduleProducts;

import com.example.haccpbackend.moduleProducts.Product;

import java.util.List;

public interface IServiceProduct {



    public Product createproduct(Product product);
    public Product findproductById(Long Id);

    public Product updateproduct(Long id,Product newProduct);

    public List<Product> getAllproducts();

    public void deleteproduct(Product product);

}
