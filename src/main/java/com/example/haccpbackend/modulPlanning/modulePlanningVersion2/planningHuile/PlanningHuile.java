package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningHuile;




import com.example.haccpbackend.modulSuiviHuile.SuiviHuiles;
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
public class PlanningHuile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tache;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateCreation;

    private boolean checked;


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

    @ManyToOne
    @JoinColumn(name = "suivihuile_id", nullable = false)
    @JsonBackReference
    private SuiviHuiles suiviHuile;

    // Constructors
    public PlanningHuile() {
    }

    public PlanningHuile(String tache, boolean checked, SuiviHuiles suiviHuile) {
        this.tache = tache;
        this.checked = checked;
        this.suiviHuile = suiviHuile;
    }

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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public SuiviHuiles getSuiviHuile() {
        return suiviHuile;
    }

    public void setSuiviHuile(SuiviHuiles suiviHuile) {
        this.suiviHuile = suiviHuile;
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

}


