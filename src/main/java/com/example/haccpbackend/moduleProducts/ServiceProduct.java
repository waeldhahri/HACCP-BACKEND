package com.example.haccpbackend.moduleProducts;

import com.example.haccpbackend.moduleProducts.IServiceProduct;
import com.example.haccpbackend.moduleProducts.Product;
import com.example.haccpbackend.moduleProducts.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceProduct implements IServiceProduct {

    @Autowired
    private  ProductRepository productRepository;


    @Override
    public Product createproduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findproductById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Product updateproduct(Long id, Product newProduct) {


        return productRepository.findById(id).map(
                product -> {
                    product.setName(newProduct.getName());
                    product.setOrigine(newProduct.getOrigine());
                    product.setCategorie(newProduct.getCategorie());
                    product.setImageOfProduct(newProduct.getImageOfProduct());
                    return productRepository.save(product);
                }
        ).orElseThrow(()-> new RuntimeException(" Product not found "));


    }

    @Override
    public List<Product> getAllproducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteproduct(Product product) {
            productRepository.delete(product);
    }
}
