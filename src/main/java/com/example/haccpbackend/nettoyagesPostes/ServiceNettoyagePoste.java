package com.example.haccpbackend.nettoyagesPostes;


import com.example.haccpbackend.modulTepuratureFrigo.CategorieFrigo;
import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import com.example.haccpbackend.modulTepuratureFrigo.FrigoRequest;
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
public class ServiceNettoyagePoste implements IServiceNettoyagePoste{




    private final NettoyagePosteRepository posteRepository;

    private final CategorieNettoyageRepository categorieNettoyageRepository;

    public ServiceNettoyagePoste(NettoyagePosteRepository posteRepository, CategorieNettoyageRepository categorieNettoyageRepository) {
        this.posteRepository = posteRepository;
        this.categorieNettoyageRepository = categorieNettoyageRepository;
    }












    public List<NettoyagesPoste> findNettoyagesPosteByCategorie(String categorieName){


        return posteRepository.findByCategorieNettoyage_NameIgnoreCase(categorieName);


    }






    @Override
    public NettoyagesPoste updateNettoyagePoste(Long id, NettoyagePosteRequest nettoyagePosteRequest, MultipartFile file) {
        return null;
    }

    @Override
    public void deleteNettoyagePoste(NettoyagesPoste nettoyagesPoste) {

        posteRepository.delete(nettoyagesPoste);

    }

    @Override
    public NettoyagesPoste createNettoyagePoste(NettoyagesPoste nettoyagesPoste) {
        return posteRepository.save(nettoyagesPoste);
    }

    @Override
    public NettoyagesPoste validatePosteNettoyage(Long id, NettoyagePosteRequest nettoyagePosteRequest, MultipartFile file1,MultipartFile file2) {





            NettoyagesPoste existingPoste = posteRepository.findById(id).orElseThrow(() -> new RuntimeException("Poste not found"));

            CategorieNettoyage newCategorie = categorieNettoyageRepository.findById(nettoyagePosteRequest.getCategorieId())
                    .orElseThrow(() -> new RuntimeException(" Categorie not found"));


            existingPoste.setNote(nettoyagePosteRequest.getNote());
            existingPoste.setValide(true);
            existingPoste.setValidePar(nettoyagePosteRequest.getValidePar());
            existingPoste.setValidAt(LocalDateTime.now());
            existingPoste.setLastModifiedDay(LocalDate.now());
            existingPoste.setLastModifiedTime(LocalTime.now());



            if (file1 != null && !file1.isEmpty()) {
                try {
                    byte[] imageBytes1 = file1.getBytes();
                    existingPoste.setImageOfPosteBefore(imageBytes1);



                    // Générer l'URL complète de l'image
                    String imageUrl1 = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/api/nettoyageposte/")
                            .path("/imageBefore/")
                            .path(existingPoste.getId().toString())
                            .toUriString();


                    existingPoste.setImageBeforeUrl(imageUrl1);

                } catch (IOException e) {
                    throw new RuntimeException("Erreur lors du téléchargement du fichier", e);
                }
            }



            if (file2 != null && !file2.isEmpty()) {
                try {
                    byte[] imageBytes2 = file2.getBytes();
                    existingPoste.setImageOfPosteAfter(imageBytes2);



                    // Générer l'URL complète de l'image
                    String imageUrl2 = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/api/nettoyageposte/")
                            .path("/imageAfter/")
                            .path(existingPoste.getId().toString())
                            .toUriString();


                    existingPoste.setImageAfterUrl(imageUrl2);



                } catch (IOException e) {
                    throw new RuntimeException("Erreur lors du téléchargement du fichier", e);
                }
            }

            return posteRepository.save(existingPoste);

        }




    @Scheduled(cron = "0 0 0 * * *") // Chaque jour à minuit
    public void genererTachesPourNouveauJour() {

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = today.minusDays(1);


        // On récupère les tâches d’hier pour les répliquer
        List<NettoyagesPoste> postesHier = posteRepository.findByDateOfCreation(yesterday);



        for (NettoyagesPoste poste : postesHier) {
            NettoyagesPoste copie = new NettoyagesPoste();



            copie.setNameOfPoste(poste.getNameOfPoste());
            copie.setCategorieNettoyage(poste.getCategorieNettoyage());
            copie.setImageAfterUrl(null);
            copie.setImageBeforeUrl(null);
            copie.setImageOfPosteAfter(null);
            copie.setImageOfPosteBefore(null);
            copie.setValidAt(null);
            copie.setNote(null);
            copie.setDateOfCreation(LocalDateTime.now());
            copie.setValidePar(null);
            copie.setValide(false);
            copie.setLastModifiedDay(null);
            copie.setLastModifiedTime(null);





            posteRepository.save(copie);

        }
    }

}
