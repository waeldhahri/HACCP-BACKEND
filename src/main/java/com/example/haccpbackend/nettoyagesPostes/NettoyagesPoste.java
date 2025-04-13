package com.example.haccpbackend.nettoyagesPostes;


import com.example.haccpbackend.modulTepuratureFrigo.CategorieFrigo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class NettoyagesPoste {







    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false )
    private String nameOfPoste ;

    @Column(nullable = false )
    @CreatedDate
    private LocalDateTime dateOfCreation;




    private String note;

    @CreatedBy
    private String validePar; // Nom de la personne qui a validé

    @Column(nullable = false )
    private boolean valide;





    @LastModifiedDate
    @Column(name = "validAt")
    private LocalDateTime validAt;

    @Column(name = "last_modified_day")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastModifiedDay;

    @Column(name = "last_modified_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime lastModifiedTime;

    @PrePersist
    @PreUpdate
    private void updateDateAndTime() {
        if (validAt != null) {
            this.lastModifiedDay = validAt.toLocalDate();
            this.lastModifiedTime = validAt.toLocalTime();
        }
    }



    @ManyToOne()
    @JoinColumn(nullable = false, name = "categorieNettoyage_id")
    private CategorieNettoyage categorieNettoyage;



    @JsonIgnore
    @Lob
    private byte[] imageOfPosteBefore;


    @JsonIgnore
    @Lob
    private byte[] imageOfPosteAfter;

    private String imageBeforeUrl ;

    private String imageAfterUrl ;







    public NettoyagesPoste() {
    }

    public NettoyagesPoste(Long id, String nameOfPoste, LocalDateTime dateOfCreation, String note, String validePar,
                           boolean valide, LocalDateTime validAt, LocalDate lastModifiedDay, LocalTime lastModifiedTime,
                           CategorieNettoyage categorieNettoyage, byte[] imageOfPosteBefore, byte[] imageOfPosteAfter,
                           String imageBeforeUrl, String imageAfterUrl) {
        this.id = id;
        this.nameOfPoste = nameOfPoste;
        this.dateOfCreation = dateOfCreation;
        this.note = note;
        this.validePar = validePar;
        this.valide = valide;
        this.validAt = validAt;
        this.lastModifiedDay = lastModifiedDay;
        this.lastModifiedTime = lastModifiedTime;
        this.categorieNettoyage = categorieNettoyage;
        this.imageOfPosteBefore = imageOfPosteBefore;
        this.imageOfPosteAfter = imageOfPosteAfter;
        this.imageBeforeUrl = imageBeforeUrl;
        this.imageAfterUrl = imageAfterUrl;
    }








    /*
    @PrePersist
    protected void onCreate() {
        date = LocalDateTime.now();
    }*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfPoste() {
        return nameOfPoste;
    }

    public void setNameOfPoste(String nameOfPoste) {
        this.nameOfPoste = nameOfPoste;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }



    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getValidePar() {
        return validePar;
    }

    public void setValidePar(String validePar) {
        this.validePar = validePar;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public LocalDateTime getValidAt() {
        return validAt;
    }

    public void setValidAt(LocalDateTime validAt) {
        this.validAt = validAt;
    }

    public LocalDate getLastModifiedDay() {
        return lastModifiedDay;
    }

    public void setLastModifiedDay(LocalDate lastModifiedDay) {
        this.lastModifiedDay = lastModifiedDay;
    }

    public LocalTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public CategorieNettoyage getCategorieNettoyage() {
        return categorieNettoyage;
    }

    public void setCategorieNettoyage(CategorieNettoyage categorieNettoyage) {
        this.categorieNettoyage = categorieNettoyage;
    }

    public byte[] getImageOfPosteBefore() {
        return imageOfPosteBefore;
    }

    public void setImageOfPosteBefore(byte[] imageOfPosteBefore) {
        this.imageOfPosteBefore = imageOfPosteBefore;
    }

    public byte[] getImageOfPosteAfter() {
        return imageOfPosteAfter;
    }

    public void setImageOfPosteAfter(byte[] imageOfPosteAfter) {
        this.imageOfPosteAfter = imageOfPosteAfter;
    }

    public String getImageBeforeUrl() {
        return imageBeforeUrl;
    }

    public void setImageBeforeUrl(String imageBeforeUrl) {
        this.imageBeforeUrl = imageBeforeUrl;
    }

    public String getImageAfterUrl() {
        return imageAfterUrl;
    }

    public void setImageAfterUrl(String imageAfterUrl) {
        this.imageAfterUrl = imageAfterUrl;
    }
}
