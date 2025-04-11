package com.example.haccpbackend.modulTepuratureFrigo;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieFrigoService implements IServiceCategorieFrigo {



    private final CategorieFrigoRepository categorieFrigoRepository;


    public CategorieFrigoService(CategorieFrigoRepository categorieFrigoRepository) {
        this.categorieFrigoRepository = categorieFrigoRepository;
    }



    @Override
    public CategorieFrigo createCategorieFrigo(CategorieFrigo categorieFrigo) {
        return categorieFrigoRepository.save(categorieFrigo);
    }



    @Override
    public List<CategorieFrigo> findCategorieFrigoByname(String name) {
        return categorieFrigoRepository.findByNameIgnoreCase(name);
    }

    @Override
    public List<CategorieFrigo> findAllCategoriesFrigo() {

        return categorieFrigoRepository.findAll();

    }


    @Override
    public void deleteCategorieFrigo(CategorieFrigo categorieFrigo) {

        categorieFrigoRepository.delete(categorieFrigo);

    }


}
