package com.example.haccpbackend.modulFournisseur;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FournisseurService {




    @Autowired
    private FournisseurRepository fournisseurRepository;





    // Ajouter un fournisseur
    public Fournisseur addFournisseur(Fournisseur fournisseur) {

        return fournisseurRepository.save(fournisseur);

    }


    public List<Fournisseur> getAllFourniseurs(){
        return fournisseurRepository.findAll() ;
    }


    public Fournisseur getFournisseurById(Long id){

        return fournisseurRepository.findById(id).get();

        }







    // Modifier un fournisseur
    public Fournisseur updateFournisseur(Long id, Fournisseur newFournisseur) {
        return fournisseurRepository.findById(id).map(fournisseur -> {

            fournisseur.setName(newFournisseur.getName());
            fournisseur.setEmail(newFournisseur.getEmail());
            fournisseur.setPhone(newFournisseur.getPhone());
            fournisseur.setAddress(newFournisseur.getAddress());






            return fournisseurRepository.save(fournisseur);
        }).orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
    }


    public void deleteFournisseur(Fournisseur fournisseur){

        fournisseurRepository.delete(fournisseur);
    }

/*

    //ajouter une interaction
    public FournisseurInteraction addInteraction ( Long id , FournisseurInteraction interaction){

        return fournisseurRepository.findById(id).map(fournisseur -> {

            interaction.setInteractionType(interaction.getInteractionType());
            interaction.setTimestamp(interaction.getTimestamp());
            interaction.setDetails(interaction.getDetails());
            interaction.setFournisseurs(fournisseurRepository.findById(id).get());

            return fournisseurInteractionRepo.save(interaction);

                }

        ).orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));}


    // Récupérer l'historique des interactions d'un fournisseur
    public List<FournisseurInteraction> getFounisseurInteraction(Long fournisseurId){
        return fournisseurInteractionRepo.findByFournisseursId(fournisseurId);
    }


*/












}
