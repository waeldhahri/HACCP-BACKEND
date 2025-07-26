package com.example.haccpbackend.nettoyagesPostes;




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


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;



import java.io.ByteArrayOutputStream;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;




@Service
@Transactional
public class ServiceNettoyagePoste implements IServiceNettoyagePoste{




    private final NettoyagePosteRepository posteRepository;

    private final CategorieNettoyageRepository categorieNettoyageRepository;


    @Autowired
    private JavaMailSender mailSender;

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


            System.out.println("ID reçu dans le controller: " + id);


            NettoyagesPoste existingPoste = posteRepository.findById(id).orElseThrow(() -> new RuntimeException("Poste not found"));

            CategorieNettoyage newCategorie = categorieNettoyageRepository.findById(nettoyagePosteRequest.getCategorieId())
                    .orElseThrow(() -> new RuntimeException(" Categorie not found"));


            existingPoste.setNote(nettoyagePosteRequest.getNote());
            existingPoste.setValide(true);
            existingPoste.setValidePar(nettoyagePosteRequest.getValidePar());
            existingPoste.setValidAt(LocalDateTime.now());
            existingPoste.setLastModifiedDay(LocalDate.now());
            existingPoste.setLastModifiedTime(LocalTime.now());
            existingPoste.setNameOfPoste(nettoyagePosteRequest.getNameOfPoste());



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
    @Transactional
    public void genererPostePourNouveauJour() {

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        // On récupère les tâches d’hier pour les répliquer
        List<NettoyagesPoste> postesHier=posteRepository.findByCreatedDay(yesterday);



        for (NettoyagesPoste poste : postesHier) {


            // Vérifie si une poste identique (même nom + même catégorie) existe déjà pour aujourd'hui
            boolean existeDeja = posteRepository
                    .findFirstByNameOfPosteAndCategorieNettoyageAndCreatedDay(

                            poste.getNameOfPoste(),
                            poste.getCategorieNettoyage(),
                            today
                    )
                    .isPresent();

            if (existeDeja) {
                continue; // ignore les doublons
            }








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
            copie.setCreatedDay(LocalDate.now());
            copie.setCreatedTime(LocalTime.now());





            posteRepository.save(copie);

        }
    }

/*


    @Transactional
    public byte[] generatePdfReport(List<NettoyagesPoste> nettoyages, String categorieName) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("Rapport de Nettoyage - Catégorie : " + categorieName));
            document.add(new Paragraph("Nombre total : " + nettoyages.size()));
            document.add(new Paragraph(" "));

            for (NettoyagesPoste n : nettoyages) {
                document.add(new Paragraph("Poste : " + n.getNameOfPoste()));
                document.add(new Paragraph("Date : " + n.getCreatedDay()));
                document.add(new Paragraph("Note : " + n.getNote()));
                document.add(new Paragraph("Valide : " + n.isValide()));

                if(n.isValide()){

                document.add(new Paragraph("ValideAt : " + n.getValidAt().withSecond(0)));

                }


                document.add(new Paragraph("-------------------------------------------"));
            }

            document.close();
        } catch (DocumentException e) {
            throw new IOException("Erreur lors de la génération du PDF", e);
        }

        return out.toByteArray();
    }

*/





    @Transactional
    public byte[] generatePdfReportTable(List<NettoyagesPoste> nettoyages, String categorieName) throws IOException {
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
            Paragraph title = new Paragraph("Rapport de Nettoyage poste " + categorieName, titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
   */






            // Charger l'image (logo.png dans le dossier resources/static ou src/main/resources)

            //Image logo = Image.getInstance("src/main/resources/static/haccp.png");

            Image logo = Image.getInstance("/app/images/haccp.png");
            // adapte le chemin selon ton projet





            logo.scaleToFit(90, 90); // redimensionner si nécessaire
            logo.setAlignment(Image.ALIGN_RIGHT);

// Créer une table pour logo + titre
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new float[]{70f, 30f}); // 80% pour le titre, 20% pour le logo

// Titre en rouge centré
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18 , BaseColor.BLUE);
           /* Paragraph title = new Paragraph("   Rapport de Nettoyage des poste    " +
                    "                                                                 " + categorieName , titleFont);*/

            Paragraph title = new Paragraph("   Rapport de Nettoyage des poste    "  , titleFont);




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
            Paragraph summary = new Paragraph("Nombre total du Nettoyage poste : " + nettoyages.size(), summaryFont);
            summary.setAlignment(Element.ALIGN_CENTER);
            document.add(summary);


            document.add(new Paragraph(" ")); // espace

            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Paragraph date = new Paragraph("Date : " + categorieName, dateFont);
            document.add(date);

            document.add(new Paragraph(" ")); // espace

// Créer un tableau avec 5 colonnes
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

// Définir une police en gras
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

// Créer les cellules d'en-tête avec texte en gras et centrage
            PdfPCell cell1 = new PdfPCell(new Phrase("Poste", boldFont));
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
            for (NettoyagesPoste n : nettoyages) {
                table.addCell(n.getNameOfPoste());
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
