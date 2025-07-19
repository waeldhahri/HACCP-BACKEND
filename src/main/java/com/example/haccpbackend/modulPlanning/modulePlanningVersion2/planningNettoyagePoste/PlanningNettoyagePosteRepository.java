package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningNettoyagePoste;

import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningFrigo.PlanningFrigo;
import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningHuile.PlanningHuile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PlanningNettoyagePosteRepository extends JpaRepository<PlanningNettoyagePoste,Long> {

    List<PlanningNettoyagePoste> findByDateCreation(LocalDateTime day);


    List<PlanningNettoyagePoste> findByDateCreationDayBetween(LocalDate start, LocalDate end);

    List<PlanningNettoyagePoste> findByDateCreationDay(LocalDate day);
}
