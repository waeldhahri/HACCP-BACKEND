package com.example.haccpbackend.modulDocuments;

import jakarta.persistence.*;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table( name = "documents")
public class Document {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String filePath;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = true)
    private String keywords; // Mots-clés pour la recherche

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime uploadedAt;




    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDateTime.now();
    }

    public Document(Long id, String name, String filePath, String fileType, String keywords, LocalDateTime uploadedAt) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
        this.fileType = fileType;
        this.keywords = keywords;
        this.uploadedAt = uploadedAt;
    }

    public Document() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }


    private Document(DocumentBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.filePath = builder.filePath;
        this.fileType = builder.fileType;
        this.keywords = builder.keywords;
    }



    // ✅ Classe interne statique pour le Builder
    public static class DocumentBuilder {
        private Long id;
        private String name;
        private String filePath;
        private String fileType;
        private String keywords;

        // ✅ Méthodes pour setter les attributs avec un retour fluide
        public DocumentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DocumentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DocumentBuilder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public DocumentBuilder fileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public DocumentBuilder keywords(String keywords) {
            this.keywords = keywords;
            return this;
        }

        // ✅ Méthode pour créer l'objet `Document`
        public Document build() {
            return new Document(this);
        }
    }

    // ✅ Méthode pour accéder au Builder
    public static DocumentBuilder builder() {
        return new DocumentBuilder();
    }



    // Pour affichage
    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileType='" + fileType + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }

}
