package com.example.haccpbackend.nettoyagesPostes;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieNettoyageRepository extends JpaRepository<CategorieNettoyage,Long> {




}
