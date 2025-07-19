package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningFrigo;

import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningFrigo.PlanningFrigo;
import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningHuile.PlanningHuile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PlanningFrigoRepository extends JpaRepository<PlanningFrigo,Long> {





    List<PlanningFrigo> findByDateCreation(LocalDateTime day);
    List<PlanningFrigo> findByDateCreationDayBetween(LocalDate start, LocalDate end);
    List<PlanningFrigo> findByDateCreationDay(LocalDate day);


}
