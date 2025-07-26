package com.example.haccpbackend.modulSuiviHuile;



import com.example.haccpbackend.others.mail.PageBorderEvent;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
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


import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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
            PdfWriter writer = PdfWriter.getInstance(document, out);
            writer.setPageEvent(new PageBorderEvent());
            document.open();
/*
            // Définir une police en gras pour le titre et le résumé
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16 ,  BaseColor.RED);
            Font summaryFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            // Créer et ajouter le titre
            Paragraph title = new Paragraph(" Rapport de Suivi Huile " + date, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
*/

            // Charger l'image (logo.png dans le dossier resources/static ou src/main/resources)
            Image logo = Image.getInstance("/app/images/haccp.png"); // adapte le chemin selon ton projet
            logo.scaleToFit(90, 90); // redimensionner si nécessaire
            logo.setAlignment(Image.ALIGN_RIGHT);

// Créer une table pour logo + titre
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{70f, 30f}); // 80% pour le titre, 20% pour le logo

// Titre en rouge centré
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18 , BaseColor.BLUE);
            Paragraph title = new Paragraph("Rapport de Suivi Huile des Friteuse " , titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            PdfPCell titleCell = new PdfPCell(title);
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

// Cellule du logo
            PdfPCell logoCell = new PdfPCell(logo);
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            logoCell.setVerticalAlignment(Element.ALIGN_TOP);

// Ajouter les cellules
            headerTable.addCell(titleCell);
            headerTable.addCell(logoCell);

// Ajouter le headerTable au document
            document.add(headerTable);














            Font summaryFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            // Créer et ajouter le résumé
            Paragraph summary = new Paragraph(" Nombre total de friteuses suivies : " + suiviHuiles.size(),summaryFont);
            summary.setAlignment(Element.ALIGN_CENTER);
            document.add(summary);


            document.add(new Paragraph(" ")); // espace


            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Paragraph date2 = new Paragraph("Date : " + date, dateFont);
            document.add(date2);

            document.add(new Paragraph(" ")); // espace





            // Créer un tableau avec 5 colonnes
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);



            // Définir une police en gras
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            // Créer les cellules d'en-tête avec texte en gras et centrage
            PdfPCell cell1 = new PdfPCell(new Phrase(" Nom de Friteuse ", boldFont));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cell2 = new PdfPCell(new Phrase("Date", boldFont));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cell3 = new PdfPCell(new Phrase("Note", boldFont));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cell4 = new PdfPCell(new Phrase("Valide", boldFont));
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cell5 = new PdfPCell(new Phrase("Validé à", boldFont));
            cell5.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Ajouter les cellules au tableau
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);




            // Contenu du tableau
            for (SuiviHuiles n : suiviHuiles) {

                table.addCell(n.getNameOfFriteuse());

                table.addCell(n.getCreatedDay().toString());

                if (n.getNote()==null){

                    table.addCell("-");

                } else {

                    table.addCell(n.getNote());

                }



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
