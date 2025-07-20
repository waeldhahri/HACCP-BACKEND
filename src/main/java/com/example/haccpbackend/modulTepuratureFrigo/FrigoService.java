package com.example.haccpbackend.modulTepuratureFrigo;


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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class FrigoService implements IServiceFrigo {


    @Autowired
    private JavaMailSender mailSender;

    private final FrigoRepository frigoRepository;
    private final CategorieFrigoRepository categorieFrigoRepository;



    public FrigoService(FrigoRepository frigoRepository, CategorieFrigoRepository categorieFrigoRepository) {
        this.frigoRepository = frigoRepository;
        this.categorieFrigoRepository = categorieFrigoRepository;
    }







    public List<Frigo> findFrigoByCategorie(String categorieName){


        return frigoRepository.findByCategorieFrigo_NameIgnoreCase(categorieName);

    }




    @Override
    public Frigo updateFrigo(Long id, FrigoRequest frigoRequest, MultipartFile file) {


        Frigo existingFrigo = frigoRepository.findById(id).orElseThrow(()-> new RuntimeException(" Frigo not found"));

        CategorieFrigo newCategorie= categorieFrigoRepository.findById(frigoRequest.getCategorieId())
                .orElseThrow(()-> new RuntimeException(" Categorie not found"));

        existingFrigo.setName(frigoRequest.getName());
        existingFrigo.setCategorie(newCategorie);



        if (file != null && !file.isEmpty()) {
            try {
                byte[] imageBytes = file.getBytes();
                existingFrigo.setImageOfFrigo(imageBytes);

                existingFrigo.setImageOfFrigo(imageBytes);

                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/frigo/")
                        .path("/image/")
                        .path(existingFrigo.getId().toString())
                        .toUriString();


                existingFrigo.setImageUrl(imageUrl);


            } catch (IOException e) {
                throw new RuntimeException("Erreur lors du téléchargement du fichier", e);
            }
        }

        // Sauvegarder et retourner le frigo mis à jour

        return frigoRepository.save(existingFrigo);

    }

    @Override
    public void deleteFrigo(Frigo frigo) {

        frigoRepository.delete(frigo);
    }




    @Transactional
    public byte[] generatePdfReportTable(List<NettoyagesPoste> nettoyages, String categorieName) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Titre et résumé
            document.add(new Paragraph("Rapport de Nettoyage poste " + categorieName));
            document.add(new Paragraph("Nombre total : " + nettoyages.size()));


            document.add(new Paragraph(" ")); // espace

            // Créer un tableau avec 5 colonnes
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // En-têtes de colonnes
            table.addCell("Poste");
            table.addCell("Date");
            table.addCell("Note");
            table.addCell("Valide");
            table.addCell("Validé à");

            // Contenu du tableau
            for (NettoyagesPoste n : nettoyages) {
                table.addCell(n.getNameOfPoste());
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
