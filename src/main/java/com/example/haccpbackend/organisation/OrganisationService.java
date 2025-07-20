package com.example.haccpbackend.organisation;


import com.example.haccpbackend.modulTepuratureFrigo.CategorieFrigo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrganisationService {





    private final  OrganisationRepository organisationRepository;


    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }




    public Organisation createOrganisation(Organisation organisation){

        return organisationRepository.save(organisation);
    }


    @Transactional
    public List<Organisation> getAllorganisations(){


        return organisationRepository.findAll();
    }




















}
