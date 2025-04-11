package com.example.haccpbackend.modulTepuratureFrigo;


import com.example.haccpbackend.modulProducts.Product;
import com.example.haccpbackend.modulProducts.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FrigoService implements IServiceFrigo {



    private final FrigoRepository frigoRepository;
    private final CategorieFrigoRepository categorieFrigoRepository;



    public FrigoService(FrigoRepository frigoRepository, CategorieFrigoRepository categorieFrigoRepository) {
        this.frigoRepository = frigoRepository;
        this.categorieFrigoRepository = categorieFrigoRepository;
    }







    public List<Frigo> findFrigoByCategorie(String categorieName){


        return frigoRepository.findByCategorieFrigo_NameIgnoreCase(categorieName);

    }




    @Override
    public Frigo updateFrigo(Long id, FrigoRequest frigoRequest, MultipartFile file) {


        Frigo existingFrigo = frigoRepository.findById(id).orElseThrow(()-> new RuntimeException(" Frigo not found"));

        CategorieFrigo newCategorie= categorieFrigoRepository.findById(frigoRequest.getCategorieId())
                .orElseThrow(()-> new RuntimeException(" Categorie not found"));

        existingFrigo.setName(frigoRequest.getName());
        existingFrigo.setCategorie(newCategorie);



        if (file != null && !file.isEmpty()) {
            try {
                byte[] imageBytes = file.getBytes();
                existingFrigo.setImageOfFrigo(imageBytes);

            } catch (IOException e) {
                throw new RuntimeException("Erreur lors du téléchargement du fichier", e);
            }
        }

        // Sauvegarder et retourner le produit mis à jour

        return frigoRepository.save(existingFrigo);

    }

    @Override
    public void deleteFrigo(Frigo frigo) {

        frigoRepository.delete(frigo);
    }


}
