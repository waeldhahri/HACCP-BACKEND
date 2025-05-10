package com.example.haccpbackend.modulSuiviHuile;

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
public class SuiviHuiles {


    public SuiviHuiles() {
    }


    public SuiviHuiles(Long id, String nameOfFriteuse, LocalDate dateOfCreation, String note, String validePar,
                       boolean valide, byte[] imageOfFriteuseAfter, String imageFriteuseUrl, LocalDateTime validAt,
                       LocalDate lastModifiedDay, LocalTime lastModifiedTime) {
        this.id = id;
        this.nameOfFriteuse = nameOfFriteuse;
        this.dateOfCreation = dateOfCreation;
        this.note = note;
        this.validePar = validePar;
        this.valide = valide;
        this.imageOfFriteuseAfter = imageOfFriteuseAfter;
        this.imageFriteuseUrl = imageFriteuseUrl;
        this.validAt = validAt;
        this.lastModifiedDay = lastModifiedDay;
        this.lastModifiedTime = lastModifiedTime;
    }





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false , unique = true )
    private String nameOfFriteuse ;


    @Column(nullable = false )
    @CreatedDate
    private LocalDate dateOfCreation;


    private String note;

    @CreatedBy
    private String validePar; // Nom de la personne qui a validé


    @Column(nullable = false )
    private boolean valide;


    @JsonIgnore
    @Lob
    private byte[] imageOfFriteuseAfter;

    private String imageFriteuseUrl ;


    @LastModifiedDate
    @JsonFormat(pattern = "HH:mm:ss")
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
            this.lastModifiedTime = validAt.toLocalTime().withNano(0);
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfFriteuse() {
        return nameOfFriteuse;
    }

    public void setNameOfFriteuse(String nameOfFriteuse) {
        this.nameOfFriteuse = nameOfFriteuse;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
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

    public byte[] getImageOfFriteuseAfter() {
        return imageOfFriteuseAfter;
    }

    public void setImageOfFriteuseAfter(byte[] imageOfFriteuseAfter) {
        this.imageOfFriteuseAfter = imageOfFriteuseAfter;
    }

    public String getImageFriteuseUrl() {
        return imageFriteuseUrl;
    }

    public void setImageFriteuseUrl(String imageFriteuseUrl) {
        this.imageFriteuseUrl = imageFriteuseUrl;
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
}
