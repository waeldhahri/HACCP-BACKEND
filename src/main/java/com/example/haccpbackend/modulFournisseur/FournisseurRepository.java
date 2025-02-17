package com.example.haccpbackend.modulFournisseur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur,Long>{

    Optional<Fournisseur> findByName(String name);

    Optional<Fournisseur> findByEmail(String email);

    Optional<Fournisseur> findByPhone(String phone);


}
