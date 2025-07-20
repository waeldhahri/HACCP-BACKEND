package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningFrigo;


import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningHuile.PlanningHuile;
import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import com.example.haccpbackend.modulTepuratureFrigo.FrigoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PlanningFrigoService {

    private final PlanningFrigoRepository planningFrigoRepository;
    private final FrigoRepository frigoRepository;

    public PlanningFrigoService(PlanningFrigoRepository planningFrigoRepository,
                                FrigoRepository frigoRepository) {
        this.planningFrigoRepository = planningFrigoRepository;
        this.frigoRepository = frigoRepository;
    }


    public PlanningFrigo createPlanning(PlanningFrigoDTO request) {
        Frigo frigo = frigoRepository.findById(request.getFrigoId())
                .orElseThrow(() -> new RuntimeException("Frigo introuvable"));


        PlanningFrigo planning = new PlanningFrigo(request.getTache(),request.isChecked(),frigo);
        return planningFrigoRepository.save(planning);
    }



    public List<PlanningFrigo> getAllPlanningFrigo() {
        return planningFrigoRepository.findAll();
    }

    public PlanningFrigo getPlanningFrigoById(Long id) {
        return planningFrigoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PlanningFrigo introuvable"));
    }

    public PlanningFrigo checkPlanning(Long id) {
        PlanningFrigo planning = planningFrigoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Planning Frigo non trouvé avec ID : " + id));

        planning.setChecked(true);
        return planningFrigoRepository.save(planning);
    }


    public List<PlanningFrigo> getPlanningsForToday() {

        return planningFrigoRepository.findByDateCreationDay(LocalDate.now());
    }

    public List<PlanningFrigo> getPlanningsBetweenDates(LocalDate start, LocalDate end) {
        return planningFrigoRepository.findByDateCreationDayBetween(start, end);
    }

    public List<PlanningFrigo> getPlanningFrigoForAday(LocalDate date) {

        return planningFrigoRepository.findByDateCreationDay(date);
    }


}
