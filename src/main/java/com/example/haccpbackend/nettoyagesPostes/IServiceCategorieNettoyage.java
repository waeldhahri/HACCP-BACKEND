package com.example.haccpbackend.nettoyagesPostes;

import com.example.haccpbackend.modulTepuratureFrigo.CategorieFrigo;

import java.util.List;

public interface IServiceCategorieNettoyage {



    public CategorieNettoyage createCategorieNettoyage(CategorieNettoyage categorieNettoyage);

    public List<CategorieNettoyage> findAllCategoriesNettoyage();

    public void deleteCategoriesNettoyage(CategorieNettoyage categorieNettoyage);












}
