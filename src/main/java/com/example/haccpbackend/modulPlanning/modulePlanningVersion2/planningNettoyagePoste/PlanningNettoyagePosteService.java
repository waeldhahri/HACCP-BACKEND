package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningNettoyagePoste;

import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningFrigo.PlanningFrigo;
import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningHuile.PlanningHuile;
import com.example.haccpbackend.nettoyagesPostes.NettoyagePosteRepository;
import com.example.haccpbackend.nettoyagesPostes.NettoyagesPoste;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PlanningNettoyagePosteService {

    private final PlanningNettoyagePosteRepository planningRepo;
    private final NettoyagePosteRepository nettoyagePosteRepo;

    public PlanningNettoyagePosteService(PlanningNettoyagePosteRepository planningRepo,
                                         NettoyagePosteRepository nettoyagePosteRepo) {
        this.planningRepo = planningRepo;
        this.nettoyagePosteRepo = nettoyagePosteRepo;
    }

    public PlanningNettoyagePoste createPlanning(PlanningNettoyagePosteDTO dto) {
        NettoyagesPoste poste = nettoyagePosteRepo.findById(dto.getNettoyagePosteId())
                .orElseThrow(() -> new RuntimeException("Nettoyage Poste introuvable"));
        PlanningNettoyagePoste planning = new PlanningNettoyagePoste(dto.getTache(), dto.isChecked(), poste);
        return planningRepo.save(planning);
    }

    public List<PlanningNettoyagePoste> getAll() {
        return planningRepo.findAll();
    }




    public List<PlanningNettoyagePoste> getAllPlanningNettoyagePoste() {
        return planningRepo.findAll();
    }

    public PlanningNettoyagePoste getPlanningNettoyagePosteById(Long id) {
        return planningRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("PlanningNettoyageposte introuvable"));
    }

    public PlanningNettoyagePoste checkPlanning(Long id) {
        PlanningNettoyagePoste planning = planningRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("PlanningNettoyagePoste non trouvé avec ID : " + id));

        planning.setChecked(true);
        return planningRepo.save(planning);
    }

    public List<PlanningNettoyagePoste> getPlanningsForToday() {

        return planningRepo.findByDateCreation(LocalDateTime.now());
    }


    public List<PlanningNettoyagePoste> getPlanningsBetweenDates(LocalDate start, LocalDate end) {
        return planningRepo.findByDateCreationDayBetween(start, end);
    }

    public List<PlanningNettoyagePoste> getPlanningHuileForAday(LocalDate date) {

        return planningRepo.findByDateCreationDay(date);
    }
}

