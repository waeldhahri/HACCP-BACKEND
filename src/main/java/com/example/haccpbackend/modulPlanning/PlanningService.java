package com.example.haccpbackend.modulPlanning;


import com.example.haccpbackend.nettoyagesPostes.NettoyagesPoste;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class PlanningService {



    private final PlanningRepository planningRepository;


    public PlanningService(PlanningRepository planningRepository) {
        this.planningRepository = planningRepository;
    }







    public Planning createPlanning (Planning planning){

        return planningRepository.save(planning);

    }




    @Scheduled(cron = "0 0 0 * * *") // Chaque jour à minuit
    public void genererTachesPlanningPourNouveauJour() {

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        // Récupère les tâches d’hier
        List<Planning> planningHier = planningRepository.findByCreatedDay(yesterday);

        for (Planning planning : planningHier) {

            // Vérifie si une tâche identique (même nom + même catégorie) existe déjà pour aujourd'hui
            boolean existeDeja = planningRepository
                    .findFirstByTacheAndPlanningCategorieAndCreatedDay(
                            planning.getTache(),
                            planning.getPlanningCategorie(),
                            today
                    )
                    .isPresent();

            if (existeDeja) {
                continue; // ignore les doublons
            }

            // Crée une copie non cochée
            Planning copie = new Planning();
            copie.setTache(planning.getTache());
            copie.setPlanningCategorie(planning.getPlanningCategorie());
            copie.setCreatedDay(today);
            copie.setCreatedAt(LocalDateTime.now());
            copie.setCreatedTime(LocalTime.now());
            copie.setChecked(false);

            planningRepository.save(copie);
        }
    }


}
