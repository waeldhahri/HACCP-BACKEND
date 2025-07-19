package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningFrigo;


import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
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
public class PlanningFrigo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tache; // description ou type de tâche

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateCreation;



    private boolean checked ;

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









    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime checkedTime;


    // Association avec un frigo
    @ManyToOne
    @JoinColumn(name = "frigo_id", nullable = false)
    @JsonBackReference
    private Frigo frigo;

    // Getters & Setters


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

    public Frigo getFrigo() {
        return frigo;
    }

    public void setFrigo(Frigo frigo) {
        this.frigo = frigo;
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


    // constructors


    public PlanningFrigo( String tache, boolean checked, Frigo frigo) {

        this.tache = tache;
        this.checked = checked;
        this.frigo = frigo;
    }



    public PlanningFrigo() {
    }
}
