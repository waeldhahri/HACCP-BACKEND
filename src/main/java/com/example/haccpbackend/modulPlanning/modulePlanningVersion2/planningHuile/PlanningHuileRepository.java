package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningHuile;

import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningFrigo.PlanningFrigo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PlanningHuileRepository extends JpaRepository<PlanningHuile,Long> {


    List<PlanningHuile> findByDateCreation(LocalDateTime day);
    List<PlanningHuile> findByDateCreationDayBetween(LocalDate start, LocalDate end);

    List<PlanningHuile> findByDateCreationDay(LocalDate day);
}
