package com.example.haccpbackend.modulFournisseur;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FournisseurInteractionRepo extends JpaRepository<FournisseurInteraction,Long> {


    List<FournisseurInteraction> findByFournisseursId(Long id);


}
