package com.example.haccpbackend.modulSuiviHuile;


import com.example.haccpbackend.nettoyagesPostes.CategorieNettoyage;
import com.example.haccpbackend.nettoyagesPostes.NettoyagePosteRequest;
import com.example.haccpbackend.nettoyagesPostes.NettoyagesPoste;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ServiceSuiviHuile implements IServiceSuiviHuile{


    private final SuiviHuileRepository suiviHuileRepository;


    public ServiceSuiviHuile(SuiviHuileRepository suiviHuileRepository) {
        this.suiviHuileRepository = suiviHuileRepository;
    }






    @Override
    public SuiviHuiles createFruiteuse(SuiviHuiles suiviHuiles) {
        return suiviHuileRepository.save(suiviHuiles);
    }

    @Override
    public void deleteFriteuse(SuiviHuiles suiviHuiles) {
        suiviHuileRepository.delete(suiviHuiles);
    }

    @Override
    public List<SuiviHuiles> findFriteuseDeJour() {
        return suiviHuileRepository.findByCreatedDay(LocalDate.now());
    }

    @Override
    public List<SuiviHuiles> findFriteuseByDate(LocalDate date) {
        return suiviHuileRepository.findByCreatedDay(date);
    }

    @Override
    public SuiviHuiles validateFriteuse(Long id, SuiviHuilesRequest suiviHuilesRequest, MultipartFile file1) {
        return null;
    }

    @Override
    public SuiviHuiles updateFriteuse(Long id , SuiviHuiles newFriteuse) {


        SuiviHuiles existingfriteuse = suiviHuileRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(" Friteuse not found "));


        existingfriteuse.setNameOfFriteuse(newFriteuse.getNameOfFriteuse());


        return suiviHuileRepository.save(existingfriteuse);





    }


    @Scheduled(cron = "0 0 0 * * *") // Chaque jour à minuit
    public void genererTachesHuilePourNouveauJour() {

        LocalDate today = LocalDate.now();
        ;
        LocalDate yesterday = today.minusDays(1);


        // On récupère les tâches d’hier pour les répliquer


        List<SuiviHuiles> suiviHuileHier = suiviHuileRepository.findByCreatedDay(yesterday);




        for (SuiviHuiles suiviHuiles : suiviHuileHier) {

            SuiviHuiles copie = new SuiviHuiles();

            copie.setNameOfFriteuse(suiviHuiles.getNameOfFriteuse());
            copie.setDateOfCreation(LocalDateTime.now());
            copie.setCreatedDay(LocalDate.now());
            copie.setCreatedTime(LocalTime.now());
            copie.setNote(null);
            //copie.setLastModifiedDay(null);
            //copie.setLastModifiedTime(null);
            copie.setValide(false);
            copie.setValidAt(null);
            copie.setValidePar(null);
            copie.setImageFriteuseUrl(null);
            copie.setImageOfFriteuseAfter(null);


            suiviHuileRepository.save(copie);




        }
    }





    public SuiviHuiles  validateHuile(Long id, SuiviHuileDto suiviHuileDto, MultipartFile file) {


        System.out.println("ID reçu dans le controller: " + id);

        SuiviHuiles existingFriteuse = suiviHuileRepository.findById(id).orElseThrow(() -> new RuntimeException("Friteuse not found"));

        existingFriteuse.setValide(true);
        existingFriteuse.setNote(suiviHuileDto.getNote());




        if (file != null && !file.isEmpty()) {
            try {
                byte[] imageBytes1 = file.getBytes();
                existingFriteuse.setImageOfFriteuseAfter(imageBytes1);

                // Générer l'URL complète de l'image
                String imageUrl1 = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/suiviHuile/")
                        .path("/imageAfter/")
                        .path(existingFriteuse.getId().toString())
                        .toUriString();

                existingFriteuse.setImageFriteuseUrl(imageUrl1);


            } catch (IOException e) {
                throw new RuntimeException("Erreur lors du téléchargement du fichier", e);
            }
        }



        return suiviHuileRepository.save(existingFriteuse);



    }



}
