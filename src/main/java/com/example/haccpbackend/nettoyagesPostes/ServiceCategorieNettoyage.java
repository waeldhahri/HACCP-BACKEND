package com.example.haccpbackend.nettoyagesPostes;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceCategorieNettoyage implements IServiceCategorieNettoyage{






    private final CategorieNettoyageRepository categorieNettoyageRepository;

    public ServiceCategorieNettoyage(CategorieNettoyageRepository categorieNettoyageRepository) {
        this.categorieNettoyageRepository = categorieNettoyageRepository;
    }





    @Override
    public CategorieNettoyage createCategorieNettoyage(CategorieNettoyage categorieNettoyage) {
        return categorieNettoyageRepository.save(categorieNettoyage);
    }

    @Override
    public List<CategorieNettoyage> findAllCategoriesNettoyage() {
        return categorieNettoyageRepository.findAll();
    }

    @Override
    public void deleteCategoriesNettoyage(CategorieNettoyage categorieNettoyage) {

        categorieNettoyageRepository.delete(categorieNettoyage);

    }
}
