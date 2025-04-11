package com.example.haccpbackend.modulTepuratureFrigo;

import com.example.haccpbackend.modulProducts.Product;

import java.util.List;

public interface IServiceCategorieFrigo {






    public CategorieFrigo createCategorieFrigo(CategorieFrigo categorieFrigo);

    public List<CategorieFrigo> findCategorieFrigoByname(String name);

    public List<CategorieFrigo> findAllCategoriesFrigo();

    public void deleteCategorieFrigo(CategorieFrigo categorieFrigo);







}
