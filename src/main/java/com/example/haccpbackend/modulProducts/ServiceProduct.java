package com.example.haccpbackend.modulProducts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceProduct implements IServiceProduct {

    @Autowired
    private  ProductRepository productRepository;


    @Override
    public Optional<Product> getProductByBarcode(String barcode)  {

        return productRepository.findByBarcode(barcode);
    }

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
                    product.setBarcode(newProduct.getBarcode());
                    return productRepository.save(product);
                }
        ).orElseThrow(()-> new RuntimeException(" Product not found "));


    }



    @Override
    public Product updateProductByBarcode(String barcode, Product newProduct) {


        return productRepository.findByBarcode(barcode).map(
                product -> {
                    product.setName(newProduct.getName());
                    product.setOrigine(newProduct.getOrigine());
                    product.setCategorie(newProduct.getCategorie());
                    product.setImageOfProduct(newProduct.getImageOfProduct());
                   // product.setBarcode(newProduct.getBarcode());
                    return productRepository.save(product);
                }
        ).orElseThrow(()-> new RuntimeException("Product not found with barcode: " + barcode));


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
