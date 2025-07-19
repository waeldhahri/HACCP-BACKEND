package com.example.haccpbackend.nettoyagesPostes;


import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningFrigo.PlanningFrigo;
import com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningNettoyagePoste.PlanningNettoyagePoste;
import com.example.haccpbackend.modulTepuratureFrigo.CategorieFrigo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"nameOfPoste", "categorieNettoyage_id", "createdDay"})
)
public class NettoyagesPoste {







    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false )
    private String nameOfPoste ;

    @Column(nullable = false )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime dateOfCreation;


    @Column(name = "createdDay")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDay;

    @Column(name = "createdTime")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime createdTime;






    private String note;

    @CreatedBy
    private String validePar; // Nom de la personne qui a validé

    @Column(nullable = false )
    private boolean valide;





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

        if (dateOfCreation != null) {
            this.createdDay = dateOfCreation.toLocalDate();
            this.createdTime = dateOfCreation.toLocalTime().withNano(0);
        }
    }



    @ManyToOne()
    @JoinColumn(nullable = false, name = "categorieNettoyage_id")
    private CategorieNettoyage categorieNettoyage;


    @OneToMany(mappedBy = "nettoyagePoste", cascade = CascadeType.ALL , orphanRemoval = true )
    @JsonManagedReference
    private List<PlanningNettoyagePoste> planningNettoyagePostes;



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


    public NettoyagesPoste(Long id, String nameOfPoste, LocalDateTime dateOfCreation,
                           LocalDate createdDay, LocalTime createdTime, String note, String validePar,
                           boolean valide, LocalDateTime validAt, LocalDate lastModifiedDay,
                           LocalTime lastModifiedTime, CategorieNettoyage categorieNettoyage,
                           List<PlanningNettoyagePoste> planningNettoyagePostes, byte[] imageOfPosteBefore,
                           byte[] imageOfPosteAfter, String imageBeforeUrl, String imageAfterUrl) {
        this.id = id;
        this.nameOfPoste = nameOfPoste;
        this.dateOfCreation = dateOfCreation;
        this.createdDay = createdDay;
        this.createdTime = createdTime;
        this.note = note;
        this.validePar = validePar;
        this.valide = valide;
        this.validAt = validAt;
        this.lastModifiedDay = lastModifiedDay;
        this.lastModifiedTime = lastModifiedTime;
        this.categorieNettoyage = categorieNettoyage;
        this.planningNettoyagePostes = planningNettoyagePostes;
        this.imageOfPosteBefore = imageOfPosteBefore;
        this.imageOfPosteAfter = imageOfPosteAfter;
        this.imageBeforeUrl = imageBeforeUrl;
        this.imageAfterUrl = imageAfterUrl;
    }

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


    public LocalDate getCreatedDay() {
        return createdDay;
    }

    public void setCreatedDay(LocalDate createdDay) {
        this.createdDay = createdDay;
    }

    public LocalTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalTime createdTime) {
        this.createdTime = createdTime;
    }

    public List<PlanningNettoyagePoste> getPlanningNettoyagePostes() {
        return planningNettoyagePostes;
    }

    public void setPlanningNettoyagePostes(List<PlanningNettoyagePoste> planningNettoyagePostes) {
        this.planningNettoyagePostes = planningNettoyagePostes;
    }
}
