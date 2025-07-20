package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningNettoyagePoste;

import com.example.haccpbackend.nettoyagesPostes.NettoyagesPoste;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class PlanningNettoyagePoste {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , nullable = false)
    private String tache;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateCreation;





    @Column(name = "dateCreationDay")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreationDay;

    @Column(name = "dateCreationTime")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime dateCreationTime;

    @PrePersist
    @PreUpdate
    private void updateDateAndTime() {
        if (dateCreation != null) {

            this.dateCreationDay = dateCreation.toLocalDate();
            this.dateCreationTime = dateCreation.toLocalTime().withNano(0);
        }

    }

    private boolean checked;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime checkedTime;

    @ManyToOne
    @JoinColumn(name = "nettoyage_poste_id", nullable = false)
    @JsonBackReference
    private NettoyagesPoste nettoyagePoste;

    // Getters & setters + constructeurs


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTache() {
        return tache;
    }

    public void setTache(String tache) {
        this.tache = tache;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public LocalDateTime getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(LocalDateTime checkedTime) {
        this.checkedTime = checkedTime;
    }

    public NettoyagesPoste getNettoyagePoste() {
        return nettoyagePoste;
    }

    public void setNettoyagePoste(NettoyagesPoste nettoyagePoste) {
        this.nettoyagePoste = nettoyagePoste;
    }


    public LocalDate getDateCreationDay() {
        return dateCreationDay;
    }

    public void setDateCreationDay(LocalDate dateCreationDay) {
        this.dateCreationDay = dateCreationDay;
    }

    public LocalTime getDateCreationTime() {
        return dateCreationTime;
    }

    public void setDateCreationTime(LocalTime dateCreationTime) {
        this.dateCreationTime = dateCreationTime;
    }

    //constuctors



    public PlanningNettoyagePoste( String tache,
                                  boolean checked,
                                  NettoyagesPoste nettoyagePoste) {

        this.tache = tache;

        this.checked = checked;

        this.nettoyagePoste = nettoyagePoste;
    }

    public PlanningNettoyagePoste() {
    }


}
