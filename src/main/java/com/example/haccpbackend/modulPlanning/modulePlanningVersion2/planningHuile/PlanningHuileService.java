package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningHuile;

import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningFrigo.PlanningFrigo;
import com.example.haccpbackend.modulSuiviHuile.SuiviHuileRepository;
import com.example.haccpbackend.modulSuiviHuile.SuiviHuiles;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PlanningHuileService {

    private final PlanningHuileRepository planningHuileRepository;
    private final SuiviHuileRepository suiviHuileRepository;

    public PlanningHuileService(PlanningHuileRepository planningHuileRepository,
                                SuiviHuileRepository suiviHuileRepository) {
        this.planningHuileRepository = planningHuileRepository;
        this.suiviHuileRepository = suiviHuileRepository;
    }

    public PlanningHuile createPlanning(PlanningHuileDTO dto) {
        SuiviHuiles suiviHuile = suiviHuileRepository.findById(dto.getSuiviHuileId())
                .orElseThrow(() -> new RuntimeException("Suivi huile introuvable"));

        PlanningHuile planning = new PlanningHuile(dto.getTache(), dto.isChecked(), suiviHuile);
        return planningHuileRepository.save(planning);
    }


    public List<PlanningHuile> getAllPlanningHuile() {
        return planningHuileRepository.findAll();
    }

    public PlanningHuile getPlanningHuileById(Long id) {
        return planningHuileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PlanningHuile introuvable"));
    }

    public PlanningHuile checkPlanning(Long id) {
        PlanningHuile planning = planningHuileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PlanningHuile non trouvé avec ID : " + id));

        planning.setChecked(true);
        return planningHuileRepository.save(planning);
    }

    public List<PlanningHuile> getPlanningHuileForToday() {

        return planningHuileRepository.findByDateCreation(LocalDateTime.now());
    }


    public List<PlanningHuile> getPlanningsBetweenDates(LocalDate start, LocalDate end) {
        return planningHuileRepository.findByDateCreationDayBetween(start, end);
    }


    public List<PlanningHuile> getPlanningHuileForAday(LocalDate date) {

        return planningHuileRepository.findByDateCreationDay(date);
    }
}
