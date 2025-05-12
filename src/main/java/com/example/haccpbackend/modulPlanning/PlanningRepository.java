package com.example.haccpbackend.modulPlanning;

import com.example.haccpbackend.nettoyagesPostes.NettoyagesPoste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PlanningRepository extends JpaRepository<Planning,Long> {






    Optional<Planning> findFirstByTacheAndPlanningCategorieAndCreatedDay(
            String tache,
            PlanningCategorie planningCategorie,
            LocalDate createdDay
    );




    public List<Planning> findByCreatedDay(LocalDate date);






}
