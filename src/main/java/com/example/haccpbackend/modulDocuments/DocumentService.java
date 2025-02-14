package com.example.haccpbackend.modulDocuments;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private static final String UPLOAD_DIR  = "uploads/";
    private static final String DOWNLOAD_DIR = System.getProperty("user.home") + "/Desktop/downloads/";


    @Autowired
    private  DocumentRepository documentRepository;

    public Document uploadFile(MultipartFile file, String keywords) throws IOException {
        if (file.isEmpty()) throw new IOException("Le fichier est vide");



        // Enregistrement du fichier sur le disque
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Files.createDirectories(Paths.get(UPLOAD_DIR));
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.createDirectories(filePath.getParent());
        System.out.println("Fichier enregistré à : " + filePath.toString());
        Files.write(filePath, file.getBytes());


        // Sauvegarde en base de données
        Document document = Document.builder()
                .name(file.getOriginalFilename())
                .filePath(filePath.toString())
                .fileType(file.getContentType())
                .keywords(keywords)
                .build();
        return documentRepository.save(document);
    }



    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Optional<Document> getDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    public List<Document> searchByKeyword(String keyword) {
        return documentRepository.findByKeywordsContainingIgnoreCase(keyword);
    }


    public boolean deleteDocument(Long id) throws IOException {
        Optional<Document> optionalDocument = documentRepository.findById(id);
        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            Files.deleteIfExists(Paths.get(document.getFilePath()));
            documentRepository.deleteById(id);
            return true;
        }
        return false;
    }


















}
