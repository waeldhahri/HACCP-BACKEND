package com.example.haccpbackend.modulTepuratureFrigo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FrigoRepository extends JpaRepository<Frigo,Long> {

   Optional<List<Frigo>> findByCategorie(Categorie categorie);

   List<Frigo> findByCategorie_NameIgnoreCase(String name);

}
