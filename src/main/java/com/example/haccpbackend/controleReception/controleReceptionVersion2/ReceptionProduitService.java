package com.example.haccpbackend.controleReception.controleReceptionVersion2;


import org.springframework.stereotype.Service;

@Service
public class ReceptionProduitService {





    private final ReceptionProduitRepository receptionProduitRepository;


    public ReceptionProduitService(ReceptionProduitRepository receptionProduitRepository) {
        this.receptionProduitRepository = receptionProduitRepository;
    }






}
