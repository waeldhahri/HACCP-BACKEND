package com.example.haccpbackend.ModulSuiviHuile;


import com.example.haccpbackend.modulUsers.User;
import com.example.haccpbackend.nettoyagesPostes.NettoyagesPoste;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        return suiviHuileRepository.findByDateOfCreation(LocalDate.now());
    }

    @Override
    public List<SuiviHuiles> findFriteuseByDate(LocalDate date) {
        return suiviHuileRepository.findByDateOfCreation(date);
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


        List<SuiviHuiles> suiviHuileHier = suiviHuileRepository.findByDateOfCreation(yesterday);




        for (SuiviHuiles suiviHuiles : suiviHuileHier) {

            SuiviHuiles copie = new SuiviHuiles();

            copie.setNameOfFriteuse(suiviHuiles.getNameOfFriteuse());
            copie.setDateOfCreation(LocalDate.now());
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


}
