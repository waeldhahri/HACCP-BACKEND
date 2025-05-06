package com.example.haccpbackend.modulPlanning;


import org.springframework.stereotype.Service;

@Service
public class PlanningService {



    private final PlanningRepository planningRepository;


    public PlanningService(PlanningRepository planningRepository) {
        this.planningRepository = planningRepository;
    }







    public Planning createPlanning (Planning planning){

        return planningRepository.save(planning);

    }





}
