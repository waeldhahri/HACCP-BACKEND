package com.example.haccpbackend.etiquetteProduit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategorieProduitRepository extends JpaRepository<CategorieProduit,Long> {




    Optional<CategorieProduit> findByNameIgnoreCase(String name);







}
