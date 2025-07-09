package com.example.haccpbackend.organisation;


import com.example.haccpbackend.modulTepuratureFrigo.CategorieFrigo;
import org.springframework.stereotype.Service;

@Service
public class OrganisationService {





    private final  OrganisationRepository organisationRepository;


    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }




    public Organisation createOrganisation(Organisation organisation){

        return organisationRepository.save(organisation);
    }




















}
