package com.example.haccpbackend.modulProducts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<Product> getProductByDate(LocalDate date) {
        return productRepository.findByDate(date);
    }

    @Override
    public Optional<List<Product>> getProductByProduit(String produit) {
        return productRepository.findByProduit(produit);
    }

    @Override
    public Optional<List<Product>> getProductByQuantite(Double quantite) {
        return productRepository.findByQuantiteLessThanEqual(quantite);
    }

    @Override
    public Optional<List<Product>> getProductByFournisseurId(Long fournisseurId) {
       return productRepository.findByFournisseurs_Id(fournisseurId);
    }


    @Override
    public Product updateproduct (Long id, ProductDTO productDTO, MultipartFile file) {


        // Vérifier si le produit existe
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" Product not found "));

        // Mettre à jour les champs du produit
        existingProduct.setDate(productDTO.getDate());
        existingProduct.setNumeroDuBonDeLivraison(productDTO.getNumeroDeBonLivraison());
        existingProduct.setNumCamion(productDTO.getNumeroTCamion());
        existingProduct.setPropreteCamion(productDTO.getNumeroPropreteCamion());
        existingProduct.setProduit(productDTO.getProduit());
        existingProduct.setHeureDeLivraison(productDTO.getHeureDeLivraison());
        existingProduct.setHeureDeStockage(productDTO.getHeureDeStockage());
        existingProduct.settProduit(productDTO.gettDeProduit());
        existingProduct.setIntegrite(productDTO.isIntegrite());
        existingProduct.setDlcORddm(productDTO.getDlc());
        existingProduct.setNumeroDeLot(productDTO.getNumeroDeLot());
        existingProduct.setQuantite(productDTO.getQuantite());


        // Si un fichier est fourni, mettre à jour l'image
        if (file != null && !file.isEmpty()) {
            try {
                byte[] imageBytes = file.getBytes();
                existingProduct.setImageOfProduct(imageBytes); // Stocker en base de données
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors du téléchargement du fichier", e);
            }
        }

        // Sauvegarder et retourner le produit mis à jour
        return productRepository.save(existingProduct);






/*
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
*/

    }



    @Override
    public Product updateProductByBarcode(String barcode, Product newProduct ) {


        return productRepository.findByBarcode(barcode).map(
                product -> {

                   // product.setBarcode(newProduct.getBarcode());



                    product.settProduit(newProduct.getProduit());
                    product.setNumeroDuBonDeLivraison(newProduct.getNumeroDuBonDeLivraison());
                    product.setNumCamion(newProduct.getNumCamion());
                    product.setPropreteCamion(newProduct.getPropreteCamion());
                    product.setHeureDeLivraison(newProduct.getHeureDeLivraison());
                    product.settProduit(newProduct.gettProduit());
                    product.setIntegrite(newProduct.isIntegrite());
                    product.setDlcORddm(newProduct.getDlcORddm());
                    product.setNumeroDeLot(newProduct.getNumeroDeLot());
                    product.setQuantite(newProduct.getQuantite());
                    product.setHeureDeStockage(newProduct.getHeureDeStockage());


/*
                    // Si un fichier est fourni, mettre à jour l'image
                    if (file != null && !file.isEmpty()) {
                        try {
                            byte[] imageBytes = file.getBytes();
                            existingProduct.setImageOfProduct(imageBytes); // Stocker en base de données
                        } catch (IOException e) {
                            throw new RuntimeException("Erreur lors du téléchargement du fichier", e);
                        }
                    }


                    */





                    return productRepository.save(product);
                }
        ).orElseThrow(()-> new RuntimeException("Product not found with barcode: " + barcode));


    }

    @Override
    public List<Product> getAllproducts() {
       // return productRepository.findAll();

        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(Product::getIdProduit).reversed())
                .collect(Collectors.toList());

    }

    @Override
    public void deleteproduct(Product product) {
            productRepository.delete(product);
    }




/*



    public List<String> getProductNamesByCategorie(List<String> categories) {

        List<String> productNames = productRepository.findProductNamesByCategories(categories);
        System.out.println("Produits trouvés : " + productNames); // Debug
        return productNames;




        //return productRepository.findProductNamesByCategorie(categorie);


     }
*/





}
