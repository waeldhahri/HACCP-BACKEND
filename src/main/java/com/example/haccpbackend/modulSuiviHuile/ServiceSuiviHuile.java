package com.example.haccpbackend.modulSuiviHuile;


import com.example.haccpbackend.nettoyagesPostes.CategorieNettoyage;
import com.example.haccpbackend.nettoyagesPostes.NettoyagePosteRequest;
import com.example.haccpbackend.nettoyagesPostes.NettoyagesPoste;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class ServiceSuiviHuile implements IServiceSuiviHuile{


    private final SuiviHuileRepository suiviHuileRepository;


    @Autowired
    private JavaMailSender mailSender;

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






    @Transactional
    public byte[] generatePdfReportTable(List<SuiviHuiles> suiviHuiles, String date) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Titre et résumé
            document.add(new Paragraph("Rapport de Suivi Huile " + date));
            document.add(new Paragraph("Nombre total : " + suiviHuiles.size()));


            document.add(new Paragraph(" ")); // espace

            // Créer un tableau avec 5 colonnes
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // En-têtes de colonnes
            table.addCell(" nom de Friteuse ");
            table.addCell("Date");
            table.addCell("Note");
            table.addCell("Valide");
            table.addCell("Validé à");

            // Contenu du tableau
            for (SuiviHuiles n : suiviHuiles) {

                table.addCell(n.getNameOfFriteuse());
                table.addCell(n.getCreatedDay().toString());
                table.addCell(n.getNote());

                if (n.isValide()) {

                    table.addCell("oui");
                    //table.addCell(String.valueOf(n.isValide()));

                }else {

                    table.addCell("non");
                }
                table.addCell(
                        (n.isValide() && n.getValidAt() != null)
                                ? n.getValidAt().withSecond(0).withNano(0).toString()
                                : "-"
                );
            }

            // Ajouter le tableau au document
            document.add(table);

            document.close();
        } catch (DocumentException e) {
            throw new IOException("Erreur lors de la génération du PDF", e);
        }

        return out.toByteArray();
    }




    public void sendEmailWithPdf(String toEmail, String subject, String body, ByteArrayOutputStream pdfStream)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body);
        helper.addAttachment("rapport.pdf", new ByteArrayResource(pdfStream.toByteArray()));

        mailSender.send(message);
    }






}
